package com.example.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object QuestionsList : Screen("questions_list")
    object Flashcards : Screen("flashcards")
    object EnglishPractice : Screen("english_practice")
    object IntroductoryInterview : Screen("intro_interview")
    object Analytics : Screen("analytics")
    object StateOfficials : Screen("state_officials")
    object Simulation : Screen("simulation")
    object ExamResult : Screen("exam_result")
}
