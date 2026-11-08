package com.example.data.model

enum class EnglishTestType {
    READING,
    WRITING,
    VOCABULARY
}

data class EnglishPracticeItem(
    val id: Int,
    val type: EnglishTestType,
    val textEn: String,
    val textEs: String,
    val category: String,
    val phoneticOrTip: String = ""
)
