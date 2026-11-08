package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.ExamAttemptEntity
import com.example.data.local.QuestionProgressEntity
import com.example.data.model.AppLanguage
import com.example.data.model.CivicsQuestion
import com.example.data.model.QuestionCategory
import com.example.data.model.StateDirectory
import com.example.data.model.StateInfo
import com.example.data.model.UserProfile
import com.example.data.repository.CivicsRepository
import com.example.util.TtsManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class CivicsUiState(
    val allQuestions: List<CivicsQuestion> = emptyList(),
    val progressMap: Map<Int, QuestionProgressEntity> = emptyMap(),
    val recentAttempts: List<ExamAttemptEntity> = emptyList(),
    val selectedCategory: QuestionCategory? = null,
    val isStarredOnly: Boolean = false,
    val isSenior6520Only: Boolean = false,
    val searchQuery: String = "",
    val selectedState: StateInfo = StateDirectory.states.first(),
    val isSpeaking: Boolean = false,
    val showSpanishHelper: Boolean = true,
    val userProfile: UserProfile = UserProfile()
) {
    val filteredQuestions: List<CivicsQuestion>
        get() {
            return allQuestions.filter { q ->
                val matchesCategory = selectedCategory == null || q.category == selectedCategory
                val matchesStarred = !isStarredOnly || (progressMap[q.id]?.isStarred == true)
                val matches6520 = !isSenior6520Only || q.isSenior6520
                val matchesSearch = searchQuery.isBlank() ||
                        q.questionEn.contains(searchQuery, ignoreCase = true) ||
                        q.questionEs.contains(searchQuery, ignoreCase = true) ||
                        q.acceptableAnswersEn.any { it.contains(searchQuery, ignoreCase = true) } ||
                        q.id.toString() == searchQuery.trim()
                matchesCategory && matchesStarred && matches6520 && matchesSearch
            }
        }

    val masteredCount: Int get() = progressMap.values.count { it.isMastered }
    val starredCount: Int get() = progressMap.values.count { it.isStarred }
    val totalQuestions: Int get() = if (userProfile.qualifiesForSenior6520) 20 else allQuestions.size
    val readinessPercentage: Int
        get() = if (totalQuestions > 0) ((masteredCount.toFloat() / totalQuestions) * 100).toInt().coerceAtMost(100) else 0

    val passedSimulationsCount: Int get() = recentAttempts.count { it.isPassed }
}

data class FilterState(
    val category: QuestionCategory? = null,
    val isStarredOnly: Boolean = false,
    val isSenior6520Only: Boolean = false,
    val searchQuery: String = "",
    val showSpanishHelper: Boolean = true,
    val userProfile: UserProfile = UserProfile()
)

class CivicsViewModel(application: Application) : AndroidViewModel(application) {
    val repository = CivicsRepository(application)
    val ttsManager = TtsManager(application)

    private val initialProfile = repository.getUserProfile()

    private val _filterState = MutableStateFlow(
        FilterState(
            userProfile = initialProfile,
            isSenior6520Only = initialProfile.qualifiesForSenior6520
        )
    )

    val uiState: StateFlow<CivicsUiState> = combine(
        repository.getAllProgressFlow(),
        repository.getRecentAttemptsFlow(),
        _filterState,
        ttsManager.isSpeaking
    ) { progressMap, recentAttempts, filter, isSpeaking ->
        val questions = repository.getAllQuestions(filter.userProfile.selectedStateCode)
        CivicsUiState(
            allQuestions = questions,
            progressMap = progressMap,
            recentAttempts = recentAttempts,
            selectedCategory = filter.category,
            isStarredOnly = filter.isStarredOnly,
            isSenior6520Only = filter.userProfile.qualifiesForSenior6520 || filter.isSenior6520Only,
            searchQuery = filter.searchQuery,
            selectedState = StateDirectory.getState(filter.userProfile.selectedStateCode) ?: StateDirectory.states.first(),
            isSpeaking = isSpeaking,
            showSpanishHelper = filter.showSpanishHelper,
            userProfile = filter.userProfile
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        CivicsUiState(
            allQuestions = repository.getAllQuestions(initialProfile.selectedStateCode),
            userProfile = initialProfile,
            isSenior6520Only = initialProfile.qualifiesForSenior6520
        )
    )

    fun setCategory(category: QuestionCategory?) {
        _filterState.value = _filterState.value.copy(category = category)
    }

    fun toggleStarredFilter() {
        _filterState.value = _filterState.value.copy(isStarredOnly = !_filterState.value.isStarredOnly)
    }

    fun toggleSenior6520Filter() {
        _filterState.value = _filterState.value.copy(isSenior6520Only = !_filterState.value.isSenior6520Only)
    }

    fun setSearchQuery(query: String) {
        _filterState.value = _filterState.value.copy(searchQuery = query)
    }

    fun toggleSpanishHelper() {
        _filterState.value = _filterState.value.copy(showSpanishHelper = !_filterState.value.showSpanishHelper)
    }

    fun selectState(code: String) {
        val updated = _filterState.value.userProfile.copy(selectedStateCode = code)
        updateProfile(updated)
    }

    fun updateProfile(profile: UserProfile) {
        repository.updateUserProfile(profile)
        _filterState.value = _filterState.value.copy(
            userProfile = profile,
            isSenior6520Only = profile.qualifiesForSenior6520 || _filterState.value.isSenior6520Only
        )
    }

    fun refreshProfile() {
        val updated = repository.getUserProfile()
        _filterState.value = _filterState.value.copy(
            userProfile = updated,
            isSenior6520Only = updated.qualifiesForSenior6520
        )
    }

    fun setAppLanguage(lang: AppLanguage) {
        val updated = _filterState.value.userProfile.copy(language = lang)
        updateProfile(updated)
    }

    fun setAgeAndResidency(age: Int, yearsInUS: Int) {
        val updated = _filterState.value.userProfile.copy(age = age, yearsInUS = yearsInUS)
        updateProfile(updated)
    }

    fun toggleStar(questionId: Int) {
        viewModelScope.launch {
            repository.toggleStar(questionId)
        }
    }

    fun toggleMastered(questionId: Int) {
        viewModelScope.launch {
            repository.toggleMastered(questionId)
        }
    }

    fun speak(text: String) {
        ttsManager.speak(text)
    }

    fun stopAudio() {
        ttsManager.stop()
    }

    fun resetProgress() {
        viewModelScope.launch {
            repository.resetAllProgress()
        }
    }

    override fun onCleared() {
        super.onCleared()
        ttsManager.shutdown()
    }
}
