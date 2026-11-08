package com.example.data.model

enum class QuestionCategory(val titleEn: String, val titleEs: String, val description: String) {
    AMERICAN_GOVERNMENT(
        titleEn = "American Government",
        titleEs = "Gobierno Estadounidense",
        description = "Principles of Democracy, System of Government, Rights & Responsibilities"
    ),
    AMERICAN_HISTORY(
        titleEn = "American History",
        titleEs = "Historia de EE. UU.",
        description = "Colonial Period, 1800s, Recent History & Modern Era"
    ),
    INTEGRATED_CIVICS(
        titleEn = "Integrated Civics",
        titleEs = "Educación Cívica Integrada",
        description = "Geography, National Symbols & Official Holidays"
    )
}

data class CivicsQuestion(
    val id: Int,
    val questionEn: String,
    val questionEs: String,
    val acceptableAnswersEn: List<String>,
    val acceptableAnswersEs: List<String>,
    val category: QuestionCategory,
    val subcategory: String,
    val isSenior6520: Boolean = false, // Questions marked with an asterisk (*) for 65+ years old with 20+ years permanent resident
    val isDynamicOfficial: Boolean = false, // For state-dependent or elected officials questions (Q20, 23, 28, 29, 43, 44)
    val explanationEn: String = "",
    val explanationEs: String = ""
)
