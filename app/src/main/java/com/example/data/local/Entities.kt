package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question_progress")
data class QuestionProgressEntity(
    @PrimaryKey val questionId: Int,
    val isStarred: Boolean = false,
    val isMastered: Boolean = false,
    val timesCorrect: Int = 0,
    val timesIncorrect: Int = 0,
    val lastAttemptTimestamp: Long = 0L
)

@Entity(tableName = "intro_question_progress")
data class IntroQuestionProgressEntity(
    @PrimaryKey val introId: Int,
    val timesPracticed: Int = 0,
    val timesCorrect: Int = 0,
    val timesIncorrect: Int = 0,
    val isMastered: Boolean = false,
    val lastAttemptTimestamp: Long = 0L
)

@Entity(tableName = "exam_attempts")
data class ExamAttemptEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val timestamp: Long = System.currentTimeMillis(),
    val score: Int,
    val totalQuestions: Int,
    val isPassed: Boolean,
    val timeTakenSeconds: Int = 0,
    val mode: String = "SIMULATION" // "SIMULATION", "AUDIO_FIRST", "FAILED_QUESTIONS", "INTRO_N400"
)
