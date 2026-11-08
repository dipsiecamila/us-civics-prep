package com.example.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.data.datasource.CivicsQuestionsDataSource
import com.example.data.datasource.IntroductoryInterviewDataSource
import com.example.data.local.CivicsDatabase
import com.example.data.local.ExamAttemptEntity
import com.example.data.local.IntroQuestionProgressEntity
import com.example.data.local.QuestionProgressEntity
import com.example.data.model.CivicsQuestion
import com.example.data.model.IntroductoryQuestion
import com.example.data.model.QuestionCategory
import com.example.data.model.StateDirectory
import com.example.data.model.StateInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CivicsRepository(private val context: Context) {
    private val database = CivicsDatabase.getDatabase(context)
    private val dao = database.civicsDao()
    private val prefs: SharedPreferences = context.getSharedPreferences("civics_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val APP_BUILD_REVISION = 14
    }

    init {
        val lastRevision = prefs.getInt("app_build_revision", 0)
        if (lastRevision != APP_BUILD_REVISION) {
            prefs.edit()
                .putInt("app_build_revision", APP_BUILD_REVISION)
                .putBoolean("has_completed_initial_n400", false)
                .remove("n400_fullName")
                .remove("n400_dateOfBirth")
                .remove("n400_countryOfBirth")
                .remove("n400_cityOfBirth")
                .remove("n400_spouseName")
                .remove("n400_numberOfChildren")
                .remove("n400_childrenNames")
                .remove("n400_currentAddress")
                .remove("n400_occupation")
                .remove("n400_employer")
                .remove("n400_tripsOutsideUS")
                .remove("n400_yearsAsPermanentResident")
                .putInt("user_age", 35)
                .putInt("user_years_in_us", 5)
                .putString("selected_state", "CA")
                .apply()
        }
    }

    fun getAllQuestions(stateCode: String = getSelectedStateCode()): List<CivicsQuestion> {
        val state = StateDirectory.getState(stateCode) ?: StateDirectory.states.first()
        return CivicsQuestionsDataSource.getQuestionsForState(state)
    }

    fun getQuestionsByCategory(category: QuestionCategory, stateCode: String = getSelectedStateCode()): List<CivicsQuestion> =
        getAllQuestions(stateCode).filter { it.category == category }

    fun getSenior6520Questions(stateCode: String = getSelectedStateCode()): List<CivicsQuestion> =
        getAllQuestions(stateCode).filter { it.isSenior6520 }

    fun getAllIntroQuestions(): List<IntroductoryQuestion> =
        IntroductoryInterviewDataSource.questions

    fun getAllProgressFlow(): Flow<Map<Int, QuestionProgressEntity>> =
        dao.getAllProgress().map { list -> list.associateBy { it.questionId } }

    fun getAllIntroProgressFlow(): Flow<Map<Int, IntroQuestionProgressEntity>> =
        dao.getAllIntroProgress().map { list -> list.associateBy { it.introId } }

    fun getRecentAttemptsFlow(): Flow<List<ExamAttemptEntity>> =
        dao.getRecentExamAttempts()

    fun getFailedQuestionsFlow(): Flow<List<QuestionProgressEntity>> =
        dao.getFailedQuestions()

    suspend fun toggleStar(questionId: Int) {
        val current = dao.getProgressForQuestion(questionId)
        if (current == null) {
            dao.upsertProgress(QuestionProgressEntity(questionId = questionId, isStarred = true))
        } else {
            dao.updateStarred(questionId, !current.isStarred)
        }
    }

    suspend fun toggleMastered(questionId: Int) {
        val current = dao.getProgressForQuestion(questionId)
        if (current == null) {
            dao.upsertProgress(QuestionProgressEntity(questionId = questionId, isMastered = true))
        } else {
            dao.updateMastered(questionId, !current.isMastered)
        }
    }

    suspend fun recordQuestionResult(questionId: Int, isCorrect: Boolean) {
        val current = dao.getProgressForQuestion(questionId) ?: QuestionProgressEntity(questionId = questionId)
        val newCorrect = current.timesCorrect + (if (isCorrect) 1 else 0)
        val newIncorrect = current.timesIncorrect + (if (isCorrect) 0 else 1)
        // Mastery Rule: Answering once is not enough. Must be answered correctly at least 3 times
        // and have more correct than incorrect. If missed, it loses mastery immediately.
        val newMastered = if (!isCorrect) false else (newCorrect >= 3 && newCorrect > newIncorrect)
        val updated = current.copy(
            timesCorrect = newCorrect,
            timesIncorrect = newIncorrect,
            isMastered = newMastered,
            lastAttemptTimestamp = System.currentTimeMillis()
        )
        dao.upsertProgress(updated)
    }

    suspend fun recordIntroResult(introId: Int, isCorrect: Boolean) {
        val current = dao.getIntroProgress(introId) ?: IntroQuestionProgressEntity(introId = introId)
        val newCorrect = current.timesCorrect + (if (isCorrect) 1 else 0)
        val newIncorrect = current.timesIncorrect + (if (isCorrect) 0 else 1)
        val newMastered = if (!isCorrect) false else (newCorrect >= 3 && newCorrect > newIncorrect)
        val updated = current.copy(
            timesPracticed = current.timesPracticed + 1,
            timesCorrect = newCorrect,
            timesIncorrect = newIncorrect,
            isMastered = newMastered,
            lastAttemptTimestamp = System.currentTimeMillis()
        )
        dao.upsertIntroProgress(updated)
    }

    suspend fun recordExamAttempt(
        score: Int,
        totalQuestions: Int,
        isPassed: Boolean,
        timeTakenSeconds: Int,
        mode: String = "SIMULATION"
    ): Long {
        val attempt = ExamAttemptEntity(
            score = score,
            totalQuestions = totalQuestions,
            isPassed = isPassed,
            timeTakenSeconds = timeTakenSeconds,
            mode = mode
        )
        return dao.insertExamAttempt(attempt)
    }

    suspend fun resetAllProgress() {
        dao.clearAllProgress()
        dao.clearAllIntroProgress()
        dao.clearAllExamAttempts()
    }

    fun getSelectedStateCode(): String = prefs.getString("selected_state", "CA") ?: "CA"

    fun setSelectedStateCode(code: String) {
        prefs.edit().putString("selected_state", code).apply()
    }

    fun getSelectedStateInfo(): StateInfo =
        StateDirectory.getState(getSelectedStateCode()) ?: StateDirectory.states.first()

    fun getUserProfile(): com.example.data.model.UserProfile {
        val langCode = prefs.getString("ui_language", com.example.data.model.AppLanguage.SPANISH.code) ?: com.example.data.model.AppLanguage.SPANISH.code
        val lang = com.example.data.model.AppLanguage.entries.firstOrNull { it.code == langCode } ?: com.example.data.model.AppLanguage.SPANISH
        val age = prefs.getInt("user_age", 35)
        val years = prefs.getInt("user_years_in_us", 5)
        val stateCode = getSelectedStateCode()
        return com.example.data.model.UserProfile(
            language = lang,
            age = age,
            yearsInUS = years,
            selectedStateCode = stateCode
        )
    }

    fun updateUserProfile(profile: com.example.data.model.UserProfile) {
        prefs.edit()
            .putString("ui_language", profile.language.code)
            .putInt("user_age", profile.age)
            .putInt("user_years_in_us", profile.yearsInUS)
            .putString("selected_state", profile.selectedStateCode)
            .apply()
    }

    fun getN400Profile(): com.example.data.model.N400PersonalProfile {
        return com.example.data.model.N400PersonalProfile(
            fullName = prefs.getString("n400_fullName", "") ?: "",
            age = prefs.getString("n400_age", "") ?: "",
            dateOfBirth = prefs.getString("n400_dateOfBirth", "") ?: "",
            countryOfBirth = prefs.getString("n400_countryOfBirth", "") ?: "",
            cityOfBirth = prefs.getString("n400_cityOfBirth", "") ?: "",
            fatherName = prefs.getString("n400_fatherName", "") ?: "",
            isFatherUSCitizen = prefs.getBoolean("n400_isFatherUSCitizen", false),
            motherName = prefs.getString("n400_motherName", "") ?: "",
            isMotherUSCitizen = prefs.getBoolean("n400_isMotherUSCitizen", false),
            maritalStatus = prefs.getString("n400_maritalStatus", "Married") ?: "Married",
            spouseName = prefs.getString("n400_spouseName", "") ?: "",
            numberOfChildren = prefs.getString("n400_numberOfChildren", "") ?: "",
            childrenNames = prefs.getString("n400_childrenNames", "") ?: "",
            currentAddress = prefs.getString("n400_currentAddress", "") ?: "",
            yearsAtCurrentAddress = prefs.getString("n400_yearsAtCurrentAddress", "") ?: "",
            occupation = prefs.getString("n400_occupation", "") ?: "",
            employer = prefs.getString("n400_employer", "") ?: "",
            tripsOutsideUS = prefs.getString("n400_tripsOutsideUS", "") ?: "",
            yearsAsPermanentResident = prefs.getString("n400_yearsAsPermanentResident", "") ?: "",
            residenceStateCode = prefs.getString("selected_state", "CA") ?: "CA"
        )
    }

    fun saveN400Profile(profile: com.example.data.model.N400PersonalProfile) {
        val birthYear = Regex("""\b(19\d\d|20\d\d)\b""").find(profile.dateOfBirth)?.value?.toIntOrNull()
        val calculatedAge = if (birthYear != null) (2026 - birthYear) else (Regex("""\d+""").find(profile.age)?.value?.toIntOrNull() ?: 35)
        val calculatedYears = Regex("""\d+""").find(profile.yearsAsPermanentResident)?.value?.toIntOrNull() ?: 5
        val stateCode = profile.residenceStateCode.ifBlank { "CA" }

        prefs.edit()
            .putString("n400_fullName", profile.fullName)
            .putString("n400_age", profile.age)
            .putString("n400_dateOfBirth", profile.dateOfBirth)
            .putString("n400_countryOfBirth", profile.countryOfBirth)
            .putString("n400_cityOfBirth", profile.cityOfBirth)
            .putString("n400_fatherName", profile.fatherName)
            .putBoolean("n400_isFatherUSCitizen", profile.isFatherUSCitizen)
            .putString("n400_motherName", profile.motherName)
            .putBoolean("n400_isMotherUSCitizen", profile.isMotherUSCitizen)
            .putString("n400_maritalStatus", profile.maritalStatus)
            .putString("n400_spouseName", profile.spouseName)
            .putString("n400_numberOfChildren", profile.numberOfChildren)
            .putString("n400_childrenNames", profile.childrenNames)
            .putString("n400_currentAddress", profile.currentAddress)
            .putString("n400_yearsAtCurrentAddress", profile.yearsAtCurrentAddress)
            .putString("n400_occupation", profile.occupation)
            .putString("n400_employer", profile.employer)
            .putString("n400_tripsOutsideUS", profile.tripsOutsideUS)
            .putString("n400_yearsAsPermanentResident", profile.yearsAsPermanentResident)
            .putString("selected_state", stateCode)
            .putInt("user_age", calculatedAge)
            .putInt("user_years_in_us", calculatedYears)
            .apply()
    }

    fun getSavedOfficerVoiceId(): String = prefs.getString("selected_officer_voice", "officer_davis") ?: "officer_davis"

    fun saveOfficerVoiceId(id: String) {
        prefs.edit().putString("selected_officer_voice", id).apply()
    }

    fun hasCompletedInitialN400(): Boolean = prefs.getBoolean("has_completed_initial_n400", false)

    fun setInitialN400Completed(completed: Boolean) {
        prefs.edit().putBoolean("has_completed_initial_n400", completed).apply()
    }
}

