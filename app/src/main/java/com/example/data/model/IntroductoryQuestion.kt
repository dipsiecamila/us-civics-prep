package com.example.data.model

data class IntroductoryQuestion(
    val id: Int,
    val category: String,
    val questionEn: String,
    val questionEs: String,
    val sampleAnswersEn: List<String>,
    val sampleAnswersEs: List<String>,
    val officerTip: String,
    val keywordsToListenFor: List<String>
)
