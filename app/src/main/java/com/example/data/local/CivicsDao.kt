package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CivicsDao {
    @Query("SELECT * FROM question_progress")
    fun getAllProgress(): Flow<List<QuestionProgressEntity>>

    @Query("SELECT * FROM question_progress WHERE questionId = :id LIMIT 1")
    suspend fun getProgressForQuestion(id: Int): QuestionProgressEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProgress(progress: QuestionProgressEntity)

    @Query("UPDATE question_progress SET isStarred = :starred WHERE questionId = :questionId")
    suspend fun updateStarred(questionId: Int, starred: Boolean)

    @Query("UPDATE question_progress SET isMastered = :mastered WHERE questionId = :questionId")
    suspend fun updateMastered(questionId: Int, mastered: Boolean)

    @Query("SELECT * FROM question_progress WHERE timesIncorrect > 0 ORDER BY timesIncorrect DESC")
    fun getFailedQuestions(): Flow<List<QuestionProgressEntity>>

    // Intro N-400 Progress
    @Query("SELECT * FROM intro_question_progress")
    fun getAllIntroProgress(): Flow<List<IntroQuestionProgressEntity>>

    @Query("SELECT * FROM intro_question_progress WHERE introId = :id LIMIT 1")
    suspend fun getIntroProgress(id: Int): IntroQuestionProgressEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertIntroProgress(progress: IntroQuestionProgressEntity)

    // Exam attempts
    @Query("SELECT * FROM exam_attempts ORDER BY timestamp DESC LIMIT 30")
    fun getRecentExamAttempts(): Flow<List<ExamAttemptEntity>>

    @Insert
    suspend fun insertExamAttempt(attempt: ExamAttemptEntity): Long

    @Query("DELETE FROM question_progress")
    suspend fun clearAllProgress()

    @Query("DELETE FROM intro_question_progress")
    suspend fun clearAllIntroProgress()

    @Query("DELETE FROM exam_attempts")
    suspend fun clearAllExamAttempts()
}
