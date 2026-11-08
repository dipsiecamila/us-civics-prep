package com.example.data.datasource

import com.example.data.model.CivicsQuestion
import com.example.data.model.QuestionCategory
import com.example.data.model.StateInfo

object CivicsQuestionsDataSource {
    val allQuestions: List<CivicsQuestion> by lazy {
        (CivicsQuestionsPart1.questions +
         CivicsQuestionsPart2.questions +
         CivicsQuestionsPart3.questions).sortedBy { it.id }
    }

    fun getQuestionsForState(state: StateInfo): List<CivicsQuestion> {
        return allQuestions.map { q ->
            when (q.id) {
                20 -> q.copy(
                    acceptableAnswersEn = state.senators.ifEmpty { listOf("Answers will vary by state") },
                    acceptableAnswersEs = state.senators.ifEmpty { listOf("Las respuestas varían según el estado") },
                    explanationEn = "Current U.S. Senators for ${state.name}: ${state.senators.joinToString(", ")}.",
                    explanationEs = "Senadores actuales de EE. UU. por ${state.name}: ${state.senators.joinToString(", ")}."
                )
                23 -> q.copy(
                    acceptableAnswersEn = listOf("Answers will vary by congressional district in ${state.name}"),
                    acceptableAnswersEs = listOf("Las respuestas varían según el distrito congresional en ${state.name}"),
                    explanationEn = "U.S. Representatives represent your local congressional district in ${state.name}.",
                    explanationEs = "Los representantes representan su distrito congresional en ${state.name}."
                )
                43 -> q.copy(
                    acceptableAnswersEn = listOf(state.governor),
                    acceptableAnswersEs = listOf(state.governor),
                    explanationEn = "${state.governor} is the current Governor of ${state.name}.",
                    explanationEs = "${state.governor} es el Gobernador actual de ${state.name}."
                )
                44 -> q.copy(
                    acceptableAnswersEn = listOf(state.capital),
                    acceptableAnswersEs = listOf(state.capital),
                    explanationEn = "${state.capital} is the capital city of ${state.name}.",
                    explanationEs = "${state.capital} es la capital de ${state.name}."
                )
                else -> q
            }
        }
    }

    fun getQuestion(id: Int): CivicsQuestion? = allQuestions.find { it.id == id }

    fun getByCategory(category: QuestionCategory): List<CivicsQuestion> =
        allQuestions.filter { it.category == category }

    fun getSenior6520(): List<CivicsQuestion> =
        allQuestions.filter { it.isSenior6520 }

    /**
     * Generates 4 options (1 correct + 3 plausible distractors) for multiple-choice tests.
     */
    fun generateMultipleChoiceOptions(question: CivicsQuestion): List<String> {
        val correctAnswer = question.acceptableAnswersEn.first()
        val otherQuestions = allQuestions
            .filter { it.id != question.id }
            .shuffled()

        val distractors = mutableListOf<String>()
        for (q in otherQuestions) {
            val candidate = q.acceptableAnswersEn.firstOrNull { it != correctAnswer && !distractors.contains(it) }
            if (candidate != null && candidate.length < 80) {
                distractors.add(candidate)
            }
            if (distractors.size >= 3) break
        }

        // Fallback distractors if needed
        val fallbacks = listOf(
            "The Constitution", "George Washington", "Congress", "The President",
            "Fifty (50)", "July 4, 1776", "Freedom of speech", "The Supreme Court"
        )
        for (fb in fallbacks) {
            if (distractors.size >= 3) break
            if (fb != correctAnswer && !distractors.contains(fb)) {
                distractors.add(fb)
            }
        }

        return (distractors.take(3) + correctAnswer).shuffled()
    }
}
