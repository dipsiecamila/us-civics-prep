package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.data.datasource.EnglishTestDataSource
import com.example.data.model.EnglishPracticeItem
import com.example.data.model.EnglishTestType
import com.example.util.TtsManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class EnglishPracticeState(
    val selectedTab: EnglishTestType = EnglishTestType.READING,
    val readingItems: List<EnglishPracticeItem> = EnglishTestDataSource.readingSentences,
    val writingItems: List<EnglishPracticeItem> = EnglishTestDataSource.writingSentences,
    val currentWritingIndex: Int = 0,
    val userWritingInput: String = "",
    val writingCheckResult: WritingCheckResult? = null
)

data class WritingCheckResult(
    val isExactMatch: Boolean,
    val expectedText: String,
    val userText: String,
    val feedback: String
)

class EnglishPracticeViewModel(application: Application) : AndroidViewModel(application) {
    val ttsManager = TtsManager(application)

    private val _state = MutableStateFlow(EnglishPracticeState())
    val state: StateFlow<EnglishPracticeState> = _state.asStateFlow()

    fun selectTab(tab: EnglishTestType) {
        ttsManager.stop()
        _state.value = _state.value.copy(
            selectedTab = tab,
            userWritingInput = "",
            writingCheckResult = null
        )
    }

    fun speak(text: String) {
        ttsManager.speak(text)
    }

    fun onWritingInputChanged(input: String) {
        _state.value = _state.value.copy(
            userWritingInput = input,
            writingCheckResult = null
        )
    }

    fun checkWriting() {
        val current = _state.value
        val expected = current.writingItems.getOrNull(current.currentWritingIndex)?.textEn ?: return
        val user = current.userWritingInput.trim()

        // Normalize spaces and compare
        val cleanExpected = expected.replace("\\s+".toRegex(), " ")
        val cleanUser = user.replace("\\s+".toRegex(), " ")
        val isExact = cleanExpected.equals(cleanUser, ignoreCase = false)
        val isCaseInsensitiveMatch = cleanExpected.equals(cleanUser, ignoreCase = true)

        val feedback = when {
            isExact -> "Excellent! Exactly correct with punctuation and capitalization."
            isCaseInsensitiveMatch -> "Good job! Check your capitalization or punctuation."
            else -> "Keep practicing! Review the spelling and try again."
        }

        _state.value = current.copy(
            writingCheckResult = WritingCheckResult(
                isExactMatch = isExact,
                expectedText = expected,
                userText = user,
                feedback = feedback
            )
        )
    }

    fun nextWritingSentence() {
        val current = _state.value
        ttsManager.stop()
        if (current.currentWritingIndex + 1 < current.writingItems.size) {
            _state.value = current.copy(
                currentWritingIndex = current.currentWritingIndex + 1,
                userWritingInput = "",
                writingCheckResult = null
            )
        }
    }

    fun prevWritingSentence() {
        val current = _state.value
        ttsManager.stop()
        if (current.currentWritingIndex > 0) {
            _state.value = current.copy(
                currentWritingIndex = current.currentWritingIndex - 1,
                userWritingInput = "",
                writingCheckResult = null
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        ttsManager.shutdown()
    }
}
