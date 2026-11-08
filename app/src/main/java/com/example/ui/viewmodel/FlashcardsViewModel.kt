package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.datasource.CivicsQuestionsDataSource
import com.example.data.local.QuestionProgressEntity
import com.example.data.model.CivicsQuestion
import com.example.data.model.QuestionCategory
import com.example.data.repository.CivicsRepository
import com.example.util.TtsManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class FlashcardOrderMode {
    PRIORITY,   // Unseen questions first, then errors, then in-progress, then mastered
    SHUFFLED,   // Random shuffle
    NUMERICAL   // In official order: 1 to 100
}

data class FlashcardDeckState(
    val questions: List<CivicsQuestion> = emptyList(),
    val currentIndex: Int = 0,
    val isFlipped: Boolean = false,
    val selectedCategory: QuestionCategory? = null,
    val isSenior6520Only: Boolean = false,
    val isStarredOnly: Boolean = false,
    val showSpanishTranslation: Boolean = true,
    val orderMode: FlashcardOrderMode = FlashcardOrderMode.PRIORITY
) {
    val currentQuestion: CivicsQuestion?
        get() = questions.getOrNull(currentIndex)

    val progressText: String
        get() = if (questions.isNotEmpty()) "${currentIndex + 1} / ${questions.size}" else "0 / 0"
}

class FlashcardsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = CivicsRepository(application)
    val ttsManager = TtsManager(application)

    private var progressMap: Map<Int, QuestionProgressEntity> = emptyMap()

    private val _state = MutableStateFlow(FlashcardDeckState())
    val state: StateFlow<FlashcardDeckState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllProgressFlow().collect { map ->
                progressMap = map
                val current = _state.value
                if (current.orderMode == FlashcardOrderMode.PRIORITY && current.questions.isNotEmpty()) {
                    _state.value = current.copy(questions = sortByPriority(current.questions))
                }
            }
        }
        loadDeck()
    }

    fun loadDeck(
        category: QuestionCategory? = _state.value.selectedCategory,
        seniorOnly: Boolean = _state.value.isSenior6520Only,
        starredOnly: Boolean = _state.value.isStarredOnly,
        starredIds: Set<Int> = emptySet(),
        orderMode: FlashcardOrderMode = _state.value.orderMode
    ) {
        var list = CivicsQuestionsDataSource.allQuestions

        if (category != null) {
            list = list.filter { it.category == category }
        }
        if (seniorOnly) {
            list = list.filter { it.isSenior6520 }
        }
        if (starredOnly && starredIds.isNotEmpty()) {
            list = list.filter { starredIds.contains(it.id) }
        }

        val orderedList = applyOrdering(list, orderMode)

        _state.value = _state.value.copy(
            questions = orderedList,
            currentIndex = 0,
            isFlipped = false,
            selectedCategory = category,
            isSenior6520Only = seniorOnly,
            isStarredOnly = starredOnly,
            orderMode = orderMode
        )
    }

    fun setOrderMode(mode: FlashcardOrderMode) {
        val current = _state.value
        val orderedList = applyOrdering(current.questions, mode)
        _state.value = current.copy(
            orderMode = mode,
            questions = orderedList,
            currentIndex = 0,
            isFlipped = false
        )
    }

    private fun applyOrdering(list: List<CivicsQuestion>, mode: FlashcardOrderMode): List<CivicsQuestion> {
        return when (mode) {
            FlashcardOrderMode.PRIORITY -> sortByPriority(list)
            FlashcardOrderMode.SHUFFLED -> list.shuffled()
            FlashcardOrderMode.NUMERICAL -> list.sortedBy { it.id }
        }
    }

    private fun sortByPriority(list: List<CivicsQuestion>): List<CivicsQuestion> {
        return list.sortedWith { a, b ->
            val pA = progressMap[a.id]
            val pB = progressMap[b.id]
            val attemptsA = (pA?.timesCorrect ?: 0) + (pA?.timesIncorrect ?: 0)
            val attemptsB = (pB?.timesCorrect ?: 0) + (pB?.timesIncorrect ?: 0)
            // Priority 1: Unseen questions (0 attempts) FIRST
            val rankA = when {
                attemptsA == 0 -> 0
                (pA?.timesIncorrect ?: 0) > 0 -> 1 // Errors SECOND
                (pA?.isMastered == false) -> 2     // In progress THIRD
                else -> 3                          // Mastered LAST
            }
            val rankB = when {
                attemptsB == 0 -> 0
                (pB?.timesIncorrect ?: 0) > 0 -> 1
                (pB?.isMastered == false) -> 2
                else -> 3
            }
            if (rankA != rankB) rankA.compareTo(rankB)
            else (pB?.timesIncorrect ?: 0).compareTo(pA?.timesIncorrect ?: 0)
        }
    }

    fun flipCard() {
        val nextFlipped = !_state.value.isFlipped
        _state.value = _state.value.copy(isFlipped = nextFlipped)

        val q = _state.value.currentQuestion ?: return
        if (nextFlipped) {
            // Read all acceptable answers
            ttsManager.speak(q.acceptableAnswersEn.joinToString(separator = ", or "))
        } else {
            // Read question
            ttsManager.speak(q.questionEn)
        }
    }

    fun nextCard() {
        val current = _state.value
        ttsManager.stop()
        if (current.currentIndex + 1 < current.questions.size) {
            _state.value = current.copy(currentIndex = current.currentIndex + 1, isFlipped = false)
        }
    }

    fun prevCard() {
        val current = _state.value
        ttsManager.stop()
        if (current.currentIndex > 0) {
            _state.value = current.copy(currentIndex = current.currentIndex - 1, isFlipped = false)
        }
    }

    fun shuffleDeck() {
        setOrderMode(FlashcardOrderMode.SHUFFLED)
    }

    fun recordResult(questionId: Int, isCorrect: Boolean) {
        viewModelScope.launch {
            repository.recordQuestionResult(questionId, isCorrect)
            nextCard()
        }
    }

    fun markMastered(questionId: Int) {
        viewModelScope.launch {
            repository.toggleMastered(questionId)
            nextCard()
        }
    }

    fun toggleStar(questionId: Int) {
        viewModelScope.launch {
            repository.toggleStar(questionId)
        }
    }

    fun toggleSpanish() {
        _state.value = _state.value.copy(showSpanishTranslation = !_state.value.showSpanishTranslation)
    }

    fun speak(text: String) {
        ttsManager.speak(text)
    }

    override fun onCleared() {
        super.onCleared()
        ttsManager.shutdown()
    }
}
