package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.datasource.CivicsQuestionsDataSource
import com.example.data.datasource.IntroductoryInterviewDataSource
import com.example.data.model.CivicsQuestion
import com.example.data.model.IntroductoryQuestion
import com.example.data.repository.CivicsRepository
import com.example.util.AVAILABLE_OFFICER_VOICES
import com.example.util.OfficerVoice
import com.example.util.TtsManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class ExamFormat {
    MULTIPLE_CHOICE,
    ORAL_INTERVIEW
}

data class QuestionQuizItem(
    val question: CivicsQuestion,
    val options: List<String>,
    val selectedAnswer: String? = null,
    val isSelfGradedCorrect: Boolean? = null,
    val isQuestionTextRevealed: Boolean = false, // Hidden by default for audio-first testing
    val isAnswerRevealed: Boolean = false,
    val timesListened: Int = 1
)

data class IntroQuizItem(
    val question: IntroductoryQuestion,
    val isTextRevealed: Boolean = false,
    val isAnswerRevealed: Boolean = false,
    val isSelfGradedCorrect: Boolean? = null
)

data class ExamSimulationState(
    val isRunning: Boolean = false,
    val isFinished: Boolean = false,
    val format: ExamFormat = ExamFormat.MULTIPLE_CHOICE,
    val isBlindAudioMode: Boolean = true, // Sound without letters first
    val isFailedQuestionsFocus: Boolean = false,
    val isIntroPhaseActive: Boolean = false,
    val introItems: List<IntroQuizItem> = emptyList(),
    val currentIntroIndex: Int = 0,
    val totalQuestions: Int = 10,
    val currentIndex: Int = 0,
    val quizItems: List<QuestionQuizItem> = emptyList(),
    val score: Int = 0,
    val isPassed: Boolean = false,
    val timeElapsedSeconds: Int = 0,
    val currentOfficer: OfficerVoice = AVAILABLE_OFFICER_VOICES.first()
) {
    val currentItem: QuestionQuizItem?
        get() = quizItems.getOrNull(currentIndex)

    val currentIntroItem: IntroQuizItem?
        get() = introItems.getOrNull(currentIntroIndex)

    val progress: Float
        get() {
            if (isIntroPhaseActive && introItems.isNotEmpty()) {
                return (currentIntroIndex + 1).toFloat() / introItems.size
            }
            return if (quizItems.isNotEmpty()) (currentIndex + 1).toFloat() / quizItems.size else 0f
        }
}

class ExamSimulationViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = CivicsRepository(application)
    val ttsManager = TtsManager(application)

    private val _state = MutableStateFlow(ExamSimulationState())
    val state: StateFlow<ExamSimulationState> = _state.asStateFlow()

    init {
        val savedId = repository.getSavedOfficerVoiceId()
        val matchingVoice = AVAILABLE_OFFICER_VOICES.firstOrNull { it.id == savedId } ?: AVAILABLE_OFFICER_VOICES.first()
        ttsManager.setOfficerVoice(matchingVoice, playPreview = false)
        _state.value = _state.value.copy(currentOfficer = matchingVoice)
    }

    fun startSimulation(
        questionCount: Int = 100,
        format: ExamFormat = ExamFormat.MULTIPLE_CHOICE,
        blindAudio: Boolean = true,
        seniorOnly: Boolean = false,
        starredOnly: Boolean = false,
        failedOnly: Boolean = false,
        includeIntroPhase: Boolean = false,
        starredIds: Set<Int> = emptySet(),
        failedIdsWithCount: Map<Int, Int> = emptyMap(),
        attemptsCountMap: Map<Int, Int> = emptyMap(),
        correctCountMap: Map<Int, Int> = emptyMap(),
        masteredIds: Set<Int> = emptySet()
    ) {
        // Prepare intro items if requested
        val introList = if (includeIntroPhase) {
            IntroductoryInterviewDataSource.questions.shuffled().take(4).map {
                IntroQuizItem(question = it)
            }
        } else {
            emptyList()
        }

        // Mastery Comparator:
        // A question requires at least 3 correct answers to be considered known.
        // 1) Unmastered questions (<3 correct) appear first. Mastered (3+ correct) go to the end.
        // 2) Questions with higher error counts appear first.
        // 3) Questions with 0 or 1 correct answer appear before 2 correct answers.
        // 4) Questions with fewer total attempts appear first.
        val questionComparator = Comparator<CivicsQuestion> { q1, q2 ->
            val isM1 = masteredIds.contains(q1.id) || (correctCountMap[q1.id] ?: 0) >= 3
            val isM2 = masteredIds.contains(q2.id) || (correctCountMap[q2.id] ?: 0) >= 3
            if (isM1 != isM2) {
                return@Comparator if (isM1) 1 else -1
            }

            val f1 = failedIdsWithCount[q1.id] ?: 0
            val f2 = failedIdsWithCount[q2.id] ?: 0
            if (f1 != f2) {
                return@Comparator f2.compareTo(f1)
            }

            val c1 = correctCountMap[q1.id] ?: 0
            val c2 = correctCountMap[q2.id] ?: 0
            if (c1 != c2) {
                return@Comparator c1.compareTo(c2)
            }

            val a1 = attemptsCountMap[q1.id] ?: 0
            val a2 = attemptsCountMap[q2.id] ?: 0
            a1.compareTo(a2)
        }

        // Prepare Civics question pool: prioritize least mastered (<3 correct), then most failed
        val pool = when {
            failedOnly && failedIdsWithCount.isNotEmpty() -> {
                CivicsQuestionsDataSource.allQuestions
                    .filter { failedIdsWithCount.containsKey(it.id) }
                    .sortedByDescending { failedIdsWithCount[it.id] ?: 0 }
            }
            starredOnly && starredIds.isNotEmpty() ->
                CivicsQuestionsDataSource.allQuestions.filter { starredIds.contains(it.id) }
            seniorOnly ->
                CivicsQuestionsDataSource.getSenior6520().sortedWith(questionComparator)
            else ->
                CivicsQuestionsDataSource.allQuestions.sortedWith(questionComparator)
        }

        val selectedPool = pool.take(questionCount.coerceAtMost(pool.size))

        val items = selectedPool.map { q ->
            QuestionQuizItem(
                question = q,
                options = CivicsQuestionsDataSource.generateMultipleChoiceOptions(q),
                isQuestionTextRevealed = !blindAudio
            )
        }

        val startWithIntro = introList.isNotEmpty()

        _state.value = ExamSimulationState(
            isRunning = true,
            isFinished = false,
            format = format,
            isBlindAudioMode = blindAudio,
            isFailedQuestionsFocus = failedOnly,
            isIntroPhaseActive = startWithIntro,
            introItems = introList,
            currentIntroIndex = 0,
            totalQuestions = items.size,
            currentIndex = 0,
            quizItems = items,
            score = 0,
            isPassed = false,
            currentOfficer = ttsManager.currentVoice.value
        )

        // Automatically play first question audio in English without letters!
        if (startWithIntro) {
            introList.firstOrNull()?.let {
                ttsManager.speak(it.question.questionEn)
            }
        } else {
            items.firstOrNull()?.let {
                ttsManager.speak(it.question.questionEn)
            }
        }
    }

    fun playCurrentQuestionAudio() {
        val current = _state.value
        if (current.isIntroPhaseActive) {
            current.currentIntroItem?.let {
                ttsManager.speak(it.question.questionEn)
            }
        } else {
            current.currentItem?.let {
                val updatedItems = current.quizItems.toMutableList()
                updatedItems[current.currentIndex] = it.copy(timesListened = it.timesListened + 1)
                _state.value = current.copy(quizItems = updatedItems)
                ttsManager.speak(it.question.questionEn)
            }
        }
    }

    fun selectOfficerVoice(voice: OfficerVoice) {
        ttsManager.setOfficerVoice(voice, playPreview = false)
        repository.saveOfficerVoiceId(voice.id)
        _state.value = _state.value.copy(currentOfficer = voice)
        playCurrentQuestionAudio()
    }

    fun cycleOfficerVoice() {
        val nextVoice = ttsManager.cycleNextOfficerVoice()
        repository.saveOfficerVoiceId(nextVoice.id)
        _state.value = _state.value.copy(currentOfficer = nextVoice)
        playCurrentQuestionAudio()
    }


    fun toggleRevealQuestionText() {
        val current = _state.value
        if (current.isIntroPhaseActive) {
            val item = current.currentIntroItem ?: return
            val updated = current.introItems.toMutableList()
            updated[current.currentIntroIndex] = item.copy(isTextRevealed = !item.isTextRevealed)
            _state.value = current.copy(introItems = updated)
        } else {
            val item = current.currentItem ?: return
            val updated = current.quizItems.toMutableList()
            updated[current.currentIndex] = item.copy(isQuestionTextRevealed = !item.isQuestionTextRevealed)
            _state.value = current.copy(quizItems = updated)
        }
    }

    fun selectMultipleChoiceAnswer(option: String) {
        val current = _state.value
        val item = current.currentItem ?: return
        if (item.selectedAnswer != null) return // Already answered

        val isCorrect = item.question.acceptableAnswersEn.any {
            it.equals(option, ignoreCase = true) || option.contains(it, ignoreCase = true)
        }

        val updatedItems = current.quizItems.toMutableList()
        updatedItems[current.currentIndex] = item.copy(
            selectedAnswer = option,
            isAnswerRevealed = true,
            isQuestionTextRevealed = true // Reveal text after answering to review
        )

        val newScore = current.score + if (isCorrect) 1 else 0

        // Persist immediately in Room database to track failed questions!
        viewModelScope.launch {
            repository.recordQuestionResult(item.question.id, isCorrect)
        }

        _state.value = current.copy(
            quizItems = updatedItems,
            score = newScore
        )
    }

    fun submitSpokenAnswer(spokenText: String) {
        val current = _state.value
        val cleanSpeech = spokenText.trim().lowercase()

        if (current.isIntroPhaseActive) {
            val intro = current.currentIntroItem ?: return
            val matched = intro.question.keywordsToListenFor.any { keyword ->
                cleanSpeech.contains(keyword.lowercase())
            } || intro.question.sampleAnswersEn.any { ans ->
                cleanSpeech.contains(ans.lowercase()) || ans.lowercase().contains(cleanSpeech)
            }
            revealOralAnswer()
            gradeIntroAnswer(matched)
            return
        }

        val item = current.currentItem ?: return
        if (current.format == ExamFormat.MULTIPLE_CHOICE) {
            val matchedOption = item.options.firstOrNull { opt ->
                val optLower = opt.lowercase()
                cleanSpeech.contains(optLower) || optLower.contains(cleanSpeech)
            } ?: item.options.firstOrNull { opt ->
                val optIndex = item.options.indexOf(opt)
                val letter = ('a' + optIndex).toString()
                cleanSpeech == letter || cleanSpeech == "option $letter"
            }
            if (matchedOption != null) {
                selectMultipleChoiceAnswer(matchedOption)
            } else {
                val matchingAns = item.question.acceptableAnswersEn.firstOrNull { ans ->
                    cleanSpeech.contains(ans.lowercase()) || ans.lowercase().contains(cleanSpeech)
                }
                val optWithAns = item.options.firstOrNull { opt ->
                    matchingAns != null && opt.contains(matchingAns, ignoreCase = true)
                }
                if (optWithAns != null) {
                    selectMultipleChoiceAnswer(optWithAns)
                }
            }
        } else {
            val isCorrect = item.question.acceptableAnswersEn.any { ans ->
                cleanSpeech.contains(ans.lowercase()) || ans.lowercase().contains(cleanSpeech)
            }
            gradeOralAnswer(isCorrect)
        }
    }

    fun revealOralAnswer() {
        val current = _state.value
        if (current.isIntroPhaseActive) {
            val item = current.currentIntroItem ?: return
            val updated = current.introItems.toMutableList()
            updated[current.currentIntroIndex] = item.copy(isAnswerRevealed = true, isTextRevealed = true)
            _state.value = current.copy(introItems = updated)
            ttsManager.speak(item.question.sampleAnswersEn.first())
        } else {
            val item = current.currentItem ?: return
            val updatedItems = current.quizItems.toMutableList()
            updatedItems[current.currentIndex] = item.copy(isAnswerRevealed = true, isQuestionTextRevealed = true)
            _state.value = current.copy(quizItems = updatedItems)

            val answerText = item.question.acceptableAnswersEn.joinToString(separator = ", or ")
            ttsManager.speak(answerText)
        }
    }

    fun gradeOralAnswer(isCorrect: Boolean) {
        val current = _state.value
        val item = current.currentItem ?: return

        val updatedItems = current.quizItems.toMutableList()
        updatedItems[current.currentIndex] = item.copy(
            isSelfGradedCorrect = isCorrect,
            isAnswerRevealed = true,
            isQuestionTextRevealed = true
        )

        val newScore = current.score + if (isCorrect) 1 else 0

        // Persist in Room DB
        viewModelScope.launch {
            repository.recordQuestionResult(item.question.id, isCorrect)
        }

        _state.value = current.copy(
            quizItems = updatedItems,
            score = newScore
        )

        nextQuestion()
    }

    fun gradeIntroAnswer(isCorrect: Boolean) {
        val current = _state.value
        val item = current.currentIntroItem ?: return

        val updated = current.introItems.toMutableList()
        updated[current.currentIntroIndex] = item.copy(
            isSelfGradedCorrect = isCorrect,
            isAnswerRevealed = true
        )

        viewModelScope.launch {
            repository.recordIntroResult(item.question.id, isCorrect)
        }

        if (current.currentIntroIndex + 1 < current.introItems.size) {
            val nextIdx = current.currentIntroIndex + 1
            _state.value = current.copy(
                introItems = updated,
                currentIntroIndex = nextIdx
            )
            ttsManager.speak(current.introItems[nextIdx].question.questionEn)
        } else {
            // Intro phase finished, move to Civics questions!
            _state.value = current.copy(
                introItems = updated,
                isIntroPhaseActive = false,
                currentIndex = 0
            )
            current.quizItems.firstOrNull()?.let {
                ttsManager.speak(it.question.questionEn)
            }
        }
    }

    fun nextQuestion() {
        val current = _state.value
        ttsManager.stop()

        if (current.currentIndex + 1 < current.quizItems.size) {
            val nextIdx = current.currentIndex + 1
            _state.value = current.copy(currentIndex = nextIdx)
            // Play question audio automatically
            ttsManager.speak(current.quizItems[nextIdx].question.questionEn)
        } else {
            finishExam()
        }
    }

    private fun finishExam() {
        val current = _state.value
        val passingThreshold = if (current.totalQuestions == 10) 6 else (current.totalQuestions * 0.6).toInt()
        val passed = current.score >= passingThreshold

        _state.value = current.copy(
            isRunning = false,
            isFinished = true,
            isPassed = passed
        )

        viewModelScope.launch {
            repository.recordExamAttempt(
                score = current.score,
                totalQuestions = current.totalQuestions,
                isPassed = passed,
                timeTakenSeconds = current.timeElapsedSeconds,
                mode = if (current.isFailedQuestionsFocus) "FAILED_QUESTIONS" else current.format.name
            )
        }
    }

    fun speakOption(option: String) {
        ttsManager.speak(option)
    }

    fun speak(text: String) {
        ttsManager.speak(text)
    }

    fun toggleStar(questionId: Int) {
        viewModelScope.launch {
            repository.toggleStar(questionId)
        }
    }

    fun stopAudio() {
        ttsManager.stop()
    }

    override fun onCleared() {
        super.onCleared()
        ttsManager.shutdown()
    }
}
