package com.example

import com.example.data.datasource.CivicsQuestionsDataSource
import com.example.data.datasource.EnglishTestDataSource
import com.example.data.datasource.IntroductoryInterviewDataSource
import com.example.data.model.AppLanguage
import com.example.data.model.QuestionCategory
import com.example.data.model.StateDirectory
import com.example.data.model.UserProfile
import com.example.ui.util.AppStrings
import com.example.util.AVAILABLE_OFFICER_VOICES
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class CivicsTest {

    @Test
    fun testAll100QuestionsLoadedAndUnique() {
        val questions = CivicsQuestionsDataSource.allQuestions
        assertEquals(100, questions.size)

        // Verify IDs 1 to 100 without duplicates or gaps
        val ids = questions.map { it.id }.toSet()
        assertEquals(100, ids.size)
        for (i in 1..100) {
            assertTrue("Missing question #$i", ids.contains(i))
        }
    }

    @Test
    fun testQuestionsCategoriesAndContent() {
        val questions = CivicsQuestionsDataSource.allQuestions

        // Verify each category has questions
        val gov = questions.filter { it.category == QuestionCategory.AMERICAN_GOVERNMENT }
        val hist = questions.filter { it.category == QuestionCategory.AMERICAN_HISTORY }
        val integrated = questions.filter { it.category == QuestionCategory.INTEGRATED_CIVICS }

        assertTrue("Government questions should be non-empty", gov.isNotEmpty())
        assertTrue("History questions should be non-empty", hist.isNotEmpty())
        assertTrue("Integrated civics questions should be non-empty", integrated.isNotEmpty())
        assertEquals(100, gov.size + hist.size + integrated.size)

        // Verify 65/20 exemption count
        val seniorQuestions = CivicsQuestionsDataSource.getSenior6520()
        assertTrue("Senior questions should have at least 20 questions", seniorQuestions.size >= 20)
    }

    @Test
    fun testSenior6520ExemptionRule() {
        // Person under 65 does not qualify
        val youngPerson = UserProfile(age = 45, yearsInUS = 25)
        assertFalse(youngPerson.qualifiesForSenior6520)

        // Person 65+ with less than 20 years does not qualify
        val newSenior = UserProfile(age = 68, yearsInUS = 12)
        assertFalse(newSenior.qualifiesForSenior6520)

        // Person 65+ with 20+ years qualifies for 65/20 special consideration!
        val qualifiedSenior = UserProfile(age = 67, yearsInUS = 22)
        assertTrue(qualifiedSenior.qualifiesForSenior6520)

        // Exact boundary 65 years old and 20 years in US
        val exactBoundary = UserProfile(age = 65, yearsInUS = 20)
        assertTrue(exactBoundary.qualifiesForSenior6520)
    }

    @Test
    fun testAppStringsLocalization() {
        assertEquals("Inicio", AppStrings.navHome(AppLanguage.SPANISH))
        assertEquals("Home", AppStrings.navHome(AppLanguage.ENGLISH))
        assertEquals("Repetir Sonido", AppStrings.repeatSound(AppLanguage.SPANISH))
        assertEquals("Repeat Sound", AppStrings.repeatSound(AppLanguage.ENGLISH))

        // Multi-language verification for French, Chinese, Spanish
        assertEquals("Módulos de Preparación", AppStrings.preparationModulesHeader(AppLanguage.SPANISH))
        assertEquals("Modules de préparation", AppStrings.preparationModulesHeader(AppLanguage.FRENCH))
        assertEquals("準備模組", AppStrings.preparationModulesHeader(AppLanguage.CHINESE))

        assertEquals("Cartas Interactivas", AppStrings.moduleFlashcardsTitle(AppLanguage.SPANISH))
        assertEquals("Cartes interactives (Flashcards)", AppStrings.moduleFlashcardsTitle(AppLanguage.FRENCH))
        assertEquals("互動記憶卡 (Flashcards)", AppStrings.moduleFlashcardsTitle(AppLanguage.CHINESE))

        assertEquals("Nom officiel complet (Nom légal)", AppStrings.n400FullNameLabel(AppLanguage.FRENCH))
        assertEquals("法定全名 (Full Legal Name)", AppStrings.n400FullNameLabel(AppLanguage.CHINESE))
        assertEquals("¿Cuál es su nombre legal completo?", AppStrings.n400FullNameLabel(AppLanguage.SPANISH))
    }

    @Test
    fun testIntroductoryInterviewQuestions() {
        val introQuestions = IntroductoryInterviewDataSource.questions
        assertTrue("Intro questions should have at least 20 questions", introQuestions.size >= 20)

        // Verify key topics are included: name, age, parents, children, address
        val questionsText = introQuestions.map { it.questionEn.lowercase() }
        assertTrue(questionsText.any { it.contains("name") })
        assertTrue(questionsText.any { it.contains("age") || it.contains("old") })
        assertTrue(questionsText.any { it.contains("born") })
        assertTrue(questionsText.any { it.contains("parents") })
        assertTrue(questionsText.any { it.contains("children") })
        assertTrue(questionsText.any { it.contains("address") })
    }

    @Test
    fun testOfficerVoicesAvailable() {
        assertTrue("Should have multiple officer voices", AVAILABLE_OFFICER_VOICES.size >= 4)
        val names = AVAILABLE_OFFICER_VOICES.map { it.displayName }
        assertTrue(names.contains("Officer Davis"))
        assertTrue(names.contains("Officer Miller"))
    }

    @Test
    fun testMultipleChoiceGenerator() {
        val sample = CivicsQuestionsDataSource.getQuestion(1)
        assertNotNull(sample)
        val options = CivicsQuestionsDataSource.generateMultipleChoiceOptions(sample!!)

        assertEquals(4, options.size)
        assertTrue(options.contains(sample.acceptableAnswersEn.first()))
    }

    @Test
    fun testStateDirectory() {
        assertEquals(52, StateDirectory.states.size) // 50 states + DC + PR
        val ca = StateDirectory.getState("CA")
        assertNotNull(ca)
        assertEquals("Sacramento", ca?.capital)
        assertEquals("Gavin Newsom", ca?.governor)
    }

    @Test
    fun testEnglishPracticeData() {
        assertTrue(EnglishTestDataSource.readingSentences.isNotEmpty())
        assertTrue(EnglishTestDataSource.writingSentences.isNotEmpty())
    }

    @Test
    fun testTop20USLanguagesOrderedWithNativeNames() {
        val top20 = AppLanguage.top20InOrder
        assertEquals(20, top20.size)
        assertEquals(AppLanguage.ENGLISH, top20[0])
        assertEquals("English", top20[0].nativeName)
        assertEquals(AppLanguage.SPANISH, top20[1])
        assertEquals("Español", top20[1].nativeName)
        assertEquals("中文", top20[2].nativeName)
        assertEquals("Tagalog", top20[3].nativeName)
        assertEquals("Tiếng Việt", top20[4].nativeName)
        assertEquals("العربية", top20[5].nativeName)
    }

    @Test
    fun testN400PersonalDialogueGeneration() {
        val profile = com.example.data.model.N400PersonalProfile(
            fullName = "Maria Santos",
            age = "38",
            dateOfBirth = "May 4, 1986",
            cityOfBirth = "San Salvador",
            countryOfBirth = "El Salvador",
            maritalStatus = "Married",
            spouseName = "Jose Santos",
            numberOfChildren = "3",
            childrenNames = "Lucas, Mateo and Mia",
            currentAddress = "456 Oak Avenue, Houston, TX",
            yearsAtCurrentAddress = "6 years",
            occupation = "Accountant",
            employer = "Lone Star Finance",
            yearsAsPermanentResident = "5"
        )

        val dialogues = com.example.data.model.N400DialogueGenerator.generate(profile, AppLanguage.SPANISH)
        assertTrue(dialogues.size >= 8)

        val nameDialogue = dialogues.first { it.id == "1" || it.id == "dlg_name" }
        assertEquals("What is your full legal name?", nameDialogue.questionEn)
        assertTrue(nameDialogue.answerEn.contains("Maria Santos"))

        val ageDialogue = dialogues.first { it.id == "2" || it.id == "dlg_age_dob" }
        assertTrue(ageDialogue.answerEn.contains("May 4, 1986"))

        val childrenDialogue = dialogues.first { it.id == "6" || it.id == "dlg_children" }
        assertTrue(childrenDialogue.answerEn.contains("3 children"))
        assertTrue(childrenDialogue.answerEn.contains("Lucas, Mateo and Mia"))
    }
}
