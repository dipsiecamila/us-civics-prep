package com.example.util

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.speech.tts.Voice
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

data class OfficerVoice(
    val id: String,
    val displayName: String,
    val description: String,
    val avatarEmoji: String,
    val isMale: Boolean,
    val pitch: Float,
    val speed: Float,
    val locale: Locale = Locale.US
)

val AVAILABLE_OFFICER_VOICES = listOf(
    OfficerVoice(
        id = "officer_davis",
        displayName = "Officer Davis",
        description = "Female • Clear & Neutral (Standard)",
        avatarEmoji = "👩‍💼",
        isMale = false,
        pitch = 1.15f,
        speed = 0.88f,
        locale = Locale.US
    ),
    OfficerVoice(
        id = "officer_miller",
        displayName = "Officer Miller",
        description = "Male • Deep & Calm",
        avatarEmoji = "👨‍💼",
        isMale = true,
        pitch = 0.78f,
        speed = 0.86f,
        locale = Locale.US
    ),
    OfficerVoice(
        id = "officer_harris",
        displayName = "Officer Harris",
        description = "Female • Warm & Articulate",
        avatarEmoji = "👩‍⚖️",
        isMale = false,
        pitch = 1.30f,
        speed = 0.84f,
        locale = Locale.US
    ),
    OfficerVoice(
        id = "officer_jackson",
        displayName = "Officer Jackson",
        description = "Male • Friendly & Natural",
        avatarEmoji = "👨‍⚖️",
        isMale = true,
        pitch = 0.88f,
        speed = 0.90f,
        locale = Locale.US
    ),
    OfficerVoice(
        id = "officer_clark",
        displayName = "Officer Clark",
        description = "Male • Mid-Atlantic / International",
        avatarEmoji = "👮‍♂️",
        isMale = true,
        pitch = 1.00f,
        speed = 0.90f,
        locale = Locale.US
    ),
    OfficerVoice(
        id = "officer_taylor",
        displayName = "Officer Taylor",
        description = "Female • Encouraging & Patient",
        avatarEmoji = "👮‍♀️",
        isMale = false,
        pitch = 1.22f,
        speed = 0.82f,
        locale = Locale.US
    )
)

class TtsManager(context: Context) : TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = TextToSpeech(context.applicationContext, this)
    private var isInitialized = false

    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    private val _currentlySpokenText = MutableStateFlow<String?>(null)
    val currentlySpokenText: StateFlow<String?> = _currentlySpokenText.asStateFlow()

    private val _currentVoice = MutableStateFlow(AVAILABLE_OFFICER_VOICES.first())
    val currentVoice: StateFlow<OfficerVoice> = _currentVoice.asStateFlow()

    init {
        tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                _isSpeaking.value = true
            }

            override fun onDone(utteranceId: String?) {
                _isSpeaking.value = false
                _currentlySpokenText.value = null
            }

            @Deprecated("Deprecated in Java")
            override fun onError(utteranceId: String?) {
                _isSpeaking.value = false
                _currentlySpokenText.value = null
            }
        })
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.US)
            if (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED) {
                isInitialized = true
                applyVoiceSettings(_currentVoice.value)
            }
        }
    }

    private fun applyVoiceSettings(officer: OfficerVoice) {
        if (!isInitialized || tts == null) return
        try {
            tts?.setLanguage(officer.locale)
            tts?.setPitch(officer.pitch)
            tts?.setSpeechRate(officer.speed)

            // Try to match specific system voice if available
            val voices = tts?.voices
            if (!voices.isNullOrEmpty()) {
                val matchingVoice = voices.firstOrNull { v ->
                    v.locale.language == officer.locale.language &&
                    if (officer.pitch > 1.1f) v.name.contains("female", ignoreCase = true)
                    else if (officer.pitch < 0.85f) v.name.contains("male", ignoreCase = true)
                    else true
                }
                if (matchingVoice != null) {
                    tts?.voice = matchingVoice
                }
            }
        } catch (_: Exception) {
            // Fallback gracefully to default parameters
            tts?.setPitch(officer.pitch)
            tts?.setSpeechRate(officer.speed)
        }
    }

    fun getOfficerGreeting(voice: OfficerVoice): String {
        return when (voice.id) {
            "officer_davis" -> "Hello, I am Officer Davis. Are you ready for your citizenship test?"
            "officer_miller" -> "Good morning, I am Officer Miller. Please listen carefully to each question."
            "officer_harris" -> "Welcome. I am Officer Harris. I will be your immigration officer today."
            "officer_jackson" -> "Good day. I am Officer Jackson. Please take your time and speak clearly."
            "officer_clark" -> "Hello there. I am Officer Clark. Let us begin your civics examination."
            "officer_taylor" -> "Hello and welcome. I am Officer Taylor. We will do great today."
            else -> "Hello, I am your USCIS officer. Ready for your test."
        }
    }

    fun setOfficerVoice(voice: OfficerVoice, playPreview: Boolean = false) {
        _currentVoice.value = voice
        applyVoiceSettings(voice)
        if (playPreview) {
            speak(getOfficerGreeting(voice), voice)
        }
    }

    fun selectAndPreviewOfficer(voice: OfficerVoice) {
        setOfficerVoice(voice, playPreview = true)
    }

    fun cycleNextOfficerVoice(playPreview: Boolean = true): OfficerVoice {
        val currentIndex = AVAILABLE_OFFICER_VOICES.indexOfFirst { it.id == _currentVoice.value.id }
        val nextIndex = if (currentIndex >= 0) (currentIndex + 1) % AVAILABLE_OFFICER_VOICES.size else 0
        val nextVoice = AVAILABLE_OFFICER_VOICES[nextIndex]
        setOfficerVoice(nextVoice, playPreview = playPreview)
        return nextVoice
    }

    fun speak(text: String, voice: OfficerVoice? = null) {
        if (!isInitialized) return
        stop()
        if (voice != null && voice.id != _currentVoice.value.id) {
            setOfficerVoice(voice)
        } else {
            applyVoiceSettings(_currentVoice.value)
        }
        _currentlySpokenText.value = text
        _isSpeaking.value = true
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "civics_utterance_${System.currentTimeMillis()}")
    }

    fun repeatLastSound() {
        val lastText = _currentlySpokenText.value
        if (!lastText.isNullOrBlank()) {
            speak(lastText)
        }
    }

    fun stop() {
        tts?.stop()
        _isSpeaking.value = false
        _currentlySpokenText.value = null
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
    }
}
