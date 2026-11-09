package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.datasource.CivicsQuestionsDataSource
import com.example.data.datasource.IntroductoryInterviewDataSource
import com.example.data.local.ExamAttemptEntity
import com.example.data.local.IntroQuestionProgressEntity
import com.example.data.local.QuestionProgressEntity
import com.example.data.model.CivicsQuestion
import com.example.data.model.IntroductoryQuestion
import com.example.data.model.QuestionCategory
import com.example.data.model.StateDirectory
import com.example.data.model.StateInfo
import com.example.data.repository.CivicsRepository
import com.example.util.TtsManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class QuestionFailureStat(
    val question: CivicsQuestion,
    val timesIncorrect: Int,
    val timesCorrect: Int,
    val failureRatePercent: Int
)

data class IntroFailureStat(
    val introQuestion: IntroductoryQuestion,
    val timesIncorrect: Int,
    val timesCorrect: Int
)

data class CategoryPerformance(
    val categoryName: String,
    val totalQuestions: Int,
    val attemptedCount: Int,
    val correctCount: Int,
    val incorrectCount: Int,
    val accuracyPercent: Int
)

data class IntroQuestionScoreItem(
    val question: IntroductoryQuestion,
    val resolvedAnswerEn: String,
    val timesCorrect: Int,
    val timesIncorrect: Int,
    val isMastered: Boolean
)

data class StateQuestionScoreItem(
    val question: CivicsQuestion,
    val stateSpecificAnswer: String,
    val timesCorrect: Int,
    val timesIncorrect: Int,
    val isMastered: Boolean
)

data class CivicsQuestionScoreItem(
    val question: CivicsQuestion,
    val timesCorrect: Int,
    val timesIncorrect: Int,
    val isMastered: Boolean,
    val isStarred: Boolean
)

data class AnalyticsUiState(
    val totalQuestionsAnswered: Int = 0,
    val totalCorrect: Int = 0,
    val totalIncorrect: Int = 0,
    val overallAccuracy: Int = 0,
    val topFailedQuestions: List<QuestionFailureStat> = emptyList(),
    val topFailedIntroQuestions: List<IntroFailureStat> = emptyList(),
    val categoryStats: List<CategoryPerformance> = emptyList(),
    val recentExams: List<ExamAttemptEntity> = emptyList(),
    val personalQuestions: List<IntroQuestionScoreItem> = emptyList(),
    val stateQuestions: List<StateQuestionScoreItem> = emptyList(),
    val all100QuestionsRanked: List<CivicsQuestionScoreItem> = emptyList(),
    val selectedState: StateInfo = StateDirectory.states.first(),
    val n400Profile: com.example.data.model.N400PersonalProfile = com.example.data.model.N400PersonalProfile()
)

class AnalyticsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = CivicsRepository(application)
    val ttsManager = TtsManager(application)

    val state: StateFlow<AnalyticsUiState> = combine(
        repository.getAllProgressFlow(),
        repository.getAllIntroProgressFlow(),
        repository.getRecentAttemptsFlow()
    ) { progressMap, introMap, recentAttempts ->
        var totalCorrect = 0
        var totalIncorrect = 0

        val failedCivicsList = mutableListOf<QuestionFailureStat>()
        val allQuestions = CivicsQuestionsDataSource.allQuestions
        val currentState = repository.getSelectedStateInfo()
        val n400Profile = repository.getN400Profile()

        // Category stats accumulator
        val catMap = mutableMapOf<QuestionCategory, Pair<Int, Int>>()
        QuestionCategory.entries.forEach { catMap[it] = Pair(0, 0) }

        val all100ScoreItems = allQuestions.map { q ->
            val p = progressMap[q.id]
            val correct = p?.timesCorrect ?: 0
            val incorrect = p?.timesIncorrect ?: 0
            val isMast = p?.isMastered ?: false
            val isStar = p?.isStarred ?: false

            totalCorrect += correct
            totalIncorrect += incorrect

            val (curC, curI) = catMap[q.category] ?: Pair(0, 0)
            catMap[q.category] = Pair(curC + correct, curI + incorrect)

            if (incorrect > 0) {
                val totalForQ = correct + incorrect
                val failRate = if (totalForQ > 0) ((incorrect.toFloat() / totalForQ) * 100).toInt() else 0
                failedCivicsList.add(
                    QuestionFailureStat(
                        question = q,
                        timesIncorrect = incorrect,
                        timesCorrect = correct,
                        failureRatePercent = failRate
                    )
                )
            }

            CivicsQuestionScoreItem(
                question = q,
                timesCorrect = correct,
                timesIncorrect = incorrect,
                isMastered = isMast,
                isStarred = isStar
            )
        }

        // Sort 100 civics questions: FIRST the most failed, LAST the least failed / 0 misses!
        val all100Ranked = all100ScoreItems.sortedWith(
            compareByDescending<CivicsQuestionScoreItem> { it.timesIncorrect }
                .thenBy { it.timesCorrect }
                .thenBy { it.question.id }
        )

        failedCivicsList.sortByDescending { it.timesIncorrect }

        // Intro Personal Questions list with resolved answers and scores
        val failedIntroList = mutableListOf<IntroFailureStat>()
        val personalQuestionsList = IntroductoryInterviewDataSource.questions.map { intro ->
            val ip = introMap[intro.id]
            val c = ip?.timesCorrect ?: 0
            val inc = ip?.timesIncorrect ?: 0
            val mastered = ip?.isMastered ?: false

            totalCorrect += c
            totalIncorrect += inc

            if (inc > 0) {
                failedIntroList.add(
                    IntroFailureStat(
                        introQuestion = intro,
                        timesIncorrect = inc,
                        timesCorrect = c
                    )
                )
            }

            val resolved = IntroductoryInterviewDataSource.resolvePersonalSampleAnswersEn(intro.id, n400Profile)
                .firstOrNull() ?: intro.sampleAnswersEn.firstOrNull().orEmpty()

            IntroQuestionScoreItem(
                question = intro,
                resolvedAnswerEn = resolved,
                timesCorrect = c,
                timesIncorrect = inc,
                isMastered = mastered
            )
        }
        failedIntroList.sortByDescending { it.timesIncorrect }

        // State Questions (IDs 20, 23, 43, 44)
        val stateQIds = setOf(20, 23, 43, 44)
        val stateQuestionsList = allQuestions.filter { it.id in stateQIds }.map { q ->
            val p = progressMap[q.id]
            val specificAnswer = when (q.id) {
                20 -> "U.S. Senators: ${currentState.senators.joinToString(", ")}"
                23 -> "U.S. Representative: Depends on your congressional district"
                43 -> "Governor of ${currentState.name}: ${currentState.governor}"
                44 -> "Capital of ${currentState.name}: ${currentState.capital}"
                else -> q.acceptableAnswersEn.firstOrNull().orEmpty()
            }
            StateQuestionScoreItem(
                question = q,
                stateSpecificAnswer = specificAnswer,
                timesCorrect = p?.timesCorrect ?: 0,
                timesIncorrect = p?.timesIncorrect ?: 0,
                isMastered = p?.isMastered ?: false
            )
        }

        val totalAnswered = totalCorrect + totalIncorrect
        val overallAcc = if (totalAnswered > 0) ((totalCorrect.toFloat() / totalAnswered) * 100).toInt() else 0

        val categoryStatsList = QuestionCategory.entries.map { cat ->
            val qCount = allQuestions.count { it.category == cat }
            val (c, i) = catMap[cat] ?: Pair(0, 0)
            val catTotal = c + i
            val acc = if (catTotal > 0) ((c.toFloat() / catTotal) * 100).toInt() else 0
            CategoryPerformance(
                categoryName = cat.titleEn,
                totalQuestions = qCount,
                attemptedCount = catTotal,
                correctCount = c,
                incorrectCount = i,
                accuracyPercent = acc
            )
        }

        AnalyticsUiState(
            totalQuestionsAnswered = totalAnswered,
            totalCorrect = totalCorrect,
            totalIncorrect = totalIncorrect,
            overallAccuracy = overallAcc,
            topFailedQuestions = failedCivicsList,
            topFailedIntroQuestions = failedIntroList,
            categoryStats = categoryStatsList,
            recentExams = recentAttempts,
            personalQuestions = personalQuestionsList,
            stateQuestions = stateQuestionsList,
            all100QuestionsRanked = all100Ranked,
            selectedState = currentState,
            n400Profile = n400Profile
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        AnalyticsUiState()
    )

    fun speak(text: String) {
        ttsManager.speak(text)
    }

    fun speakUserAnswer(text: String, isMale: Boolean) {
        ttsManager.speakUserAnswer(text, isMale)
    }

    override fun onCleared() {
        super.onCleared()
        ttsManager.shutdown()
    }
}
