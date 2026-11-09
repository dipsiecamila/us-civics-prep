package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Hearing
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.ui.components.CivicsBottomNav
import com.example.ui.components.CivicsTopBar
import com.example.ui.navigation.Screen
import com.example.ui.screens.AnalyticsScreen
import com.example.ui.screens.EnglishPracticeScreen
import com.example.ui.screens.ExamResultScreen
import com.example.ui.screens.ExamSimulationScreen
import com.example.ui.screens.FlashcardsScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.IntroductoryInterviewScreen
import com.example.ui.screens.QuestionsListScreen
import com.example.ui.screens.StateOfficialsScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.RedSecondary
import com.example.ui.util.AppStrings
import com.example.ui.viewmodel.AnalyticsViewModel
import com.example.ui.viewmodel.CivicsViewModel
import com.example.ui.viewmodel.EnglishPracticeViewModel
import com.example.ui.viewmodel.ExamFormat
import com.example.ui.viewmodel.ExamSimulationViewModel
import com.example.ui.viewmodel.FlashcardsViewModel

class MainActivity : ComponentActivity() {
    private val civicsViewModel: CivicsViewModel by viewModels()
    private val examViewModel: ExamSimulationViewModel by viewModels()
    private val flashcardsViewModel: FlashcardsViewModel by viewModels()
    private val englishViewModel: EnglishPracticeViewModel by viewModels()
    private val analyticsViewModel: AnalyticsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainApp(
                    civicsViewModel = civicsViewModel,
                    examViewModel = examViewModel,
                    flashcardsViewModel = flashcardsViewModel,
                    englishViewModel = englishViewModel,
                    analyticsViewModel = analyticsViewModel
                )
            }
        }
    }
}

@Composable
fun MainApp(
    civicsViewModel: CivicsViewModel,
    examViewModel: ExamSimulationViewModel,
    flashcardsViewModel: FlashcardsViewModel,
    englishViewModel: EnglishPracticeViewModel,
    analyticsViewModel: AnalyticsViewModel
) {
    val isInitialN400Done = remember { civicsViewModel.repository.hasCompletedInitialN400() }
    var currentScreen by remember {
        mutableStateOf<Screen>(
            if (!isInitialN400Done) Screen.IntroductoryInterview else Screen.Home
        )
    }
    var showStartExamDialog by remember { mutableStateOf(false) }
    var showResetDialog by remember { mutableStateOf(false) }

    val civicsState by civicsViewModel.uiState.collectAsState()
    val userProfile = civicsState.userProfile
    val lang = userProfile.language

    val starredIds = remember(civicsState.progressMap) {
        civicsState.progressMap.filterValues { it.isStarred }.keys
    }
    val failedMap = remember(civicsState.progressMap) {
        civicsState.progressMap.filterValues { it.timesIncorrect > 0 }
            .mapValues { it.value.timesIncorrect }
    }

    // Exam Config State
    var selectedQuestionCount by remember { mutableIntStateOf(10) }
    var selectedExamFormat by remember { mutableStateOf(ExamFormat.MULTIPLE_CHOICE) }
    var isBlindAudioMode by remember { mutableStateOf(true) } // Audio first without text
    var isIncludeIntroPhase by remember { mutableStateOf(false) } // Include N-400 interview questions
    var isSenior6520FilterExam by remember { mutableStateOf(userProfile.qualifiesForSenior6520) }
    var isFailedOnlyExam by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            CivicsTopBar(
                selectedStateCode = civicsState.selectedState.code,
                language = lang,
                ttsManager = civicsViewModel.ttsManager,
                onLanguageChanged = { newLang -> civicsViewModel.setAppLanguage(newLang) },
                onNavigateState = { currentScreen = Screen.StateOfficials },
                onResetProgress = { showResetDialog = true },
                onNavigateHome = {
                    civicsViewModel.stopAudio()
                    currentScreen = Screen.Home
                }
            )
        },
        bottomBar = {
            CivicsBottomNav(
                currentRoute = currentScreen.route,
                language = lang,
                totalQuestions = civicsState.totalQuestions,
                onNavigate = { screen ->
                    civicsViewModel.stopAudio()
                    if (screen == Screen.Flashcards) {
                        flashcardsViewModel.loadDeck(starredIds = starredIds)
                    }
                    currentScreen = screen
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentScreen) {
                is Screen.Home -> {
                    HomeScreen(
                        uiState = civicsState,
                        onStartSimulation = {
                            selectedQuestionCount = 10
                            isFailedOnlyExam = false
                            isSenior6520FilterExam = userProfile.qualifiesForSenior6520
                            showStartExamDialog = true
                        },
                        onStartFailedQuestionsTest = {
                            val attemptsMap = civicsState.progressMap.mapValues { it.value.timesCorrect + it.value.timesIncorrect }
                            val correctMap = civicsState.progressMap.mapValues { it.value.timesCorrect }
                            val masteredSet = civicsState.progressMap.filterValues { it.isMastered || it.timesCorrect >= 3 }.keys
                            examViewModel.startSimulation(
                                questionCount = failedMap.size.coerceAtLeast(5),
                                format = ExamFormat.MULTIPLE_CHOICE,
                                blindAudio = true,
                                failedOnly = true,
                                failedIdsWithCount = failedMap,
                                attemptsCountMap = attemptsMap,
                                correctCountMap = correctMap,
                                masteredIds = masteredSet
                            )
                            currentScreen = Screen.Simulation
                        },
                        onNavigateQuestions = { cat ->
                            civicsViewModel.setCategory(cat)
                            currentScreen = Screen.QuestionsList
                        },
                        onNavigateIntroInterview = {
                            currentScreen = Screen.IntroductoryInterview
                        },
                        onNavigateAnalytics = {
                            currentScreen = Screen.Analytics
                        },
                        onNavigateFlashcards = {
                            flashcardsViewModel.loadDeck(starredIds = starredIds)
                            currentScreen = Screen.Flashcards
                        },
                        onNavigateEnglish = {
                            currentScreen = Screen.EnglishPractice
                        },
                        onNavigateStateOfficials = {
                            currentScreen = Screen.StateOfficials
                        }
                    )
                }

                is Screen.IntroductoryInterview -> {
                    IntroductoryInterviewScreen(
                        repository = civicsViewModel.repository,
                        ttsManager = civicsViewModel.ttsManager,
                        language = lang,
                        onProfileUpdated = { civicsViewModel.refreshProfile() },
                        onBack = {
                            civicsViewModel.refreshProfile()
                            currentScreen = Screen.Home
                        }
                    )
                }

                is Screen.QuestionsList -> {
                    QuestionsListScreen(
                        uiState = civicsState,
                        onSearchQueryChange = { civicsViewModel.setSearchQuery(it) },
                        onSelectCategory = { civicsViewModel.setCategory(it) },
                        onToggleStarredFilter = { civicsViewModel.toggleStarredFilter() },
                        onToggleSenior6520Filter = { civicsViewModel.toggleSenior6520Filter() },
                        onToggleSpanishHelper = { civicsViewModel.toggleSpanishHelper() },
                        onToggleStar = { civicsViewModel.toggleStar(it) },
                        onToggleMastered = { civicsViewModel.toggleMastered(it) },
                        onSpeak = { civicsViewModel.speak(it) }
                    )
                }

                is Screen.Analytics -> {
                    AnalyticsScreen(
                        viewModel = analyticsViewModel,
                        onStartFailedQuestionsTest = {
                            val attemptsMap = civicsState.progressMap.mapValues { it.value.timesCorrect + it.value.timesIncorrect }
                            val correctMap = civicsState.progressMap.mapValues { it.value.timesCorrect }
                            val masteredSet = civicsState.progressMap.filterValues { it.isMastered || it.timesCorrect >= 3 }.keys
                            examViewModel.startSimulation(
                                questionCount = failedMap.size.coerceAtLeast(5),
                                format = ExamFormat.MULTIPLE_CHOICE,
                                blindAudio = true,
                                failedOnly = true,
                                failedIdsWithCount = failedMap,
                                attemptsCountMap = attemptsMap,
                                correctCountMap = correctMap,
                                masteredIds = masteredSet
                            )
                            currentScreen = Screen.Simulation
                        },
                        onBack = { currentScreen = Screen.Home }
                    )
                }

                is Screen.Flashcards -> {
                    FlashcardsScreen(
                        viewModel = flashcardsViewModel,
                        starredIds = starredIds,
                        language = lang
                    )
                }

                is Screen.EnglishPractice -> {
                    EnglishPracticeScreen(
                        viewModel = englishViewModel,
                        language = lang
                    )
                }

                is Screen.StateOfficials -> {
                    StateOfficialsScreen(
                        selectedState = civicsState.selectedState,
                        onSelectState = { code ->
                            civicsViewModel.selectState(code)
                        },
                        onBack = { currentScreen = Screen.Home }
                    )
                }

                is Screen.Simulation -> {
                    ExamSimulationScreen(
                        viewModel = examViewModel,
                        starredIds = starredIds,
                        onFinishExam = { currentScreen = Screen.ExamResult },
                        onCancelExam = {
                            examViewModel.stopAudio()
                            currentScreen = Screen.Home
                        },
                        language = lang
                    )
                }

                is Screen.ExamResult -> {
                    ExamResultScreen(
                        viewModel = examViewModel,
                        starredIds = starredIds,
                        onRetake = {
                            val attemptsMap = civicsState.progressMap.mapValues { it.value.timesCorrect + it.value.timesIncorrect }
                            val correctMap = civicsState.progressMap.mapValues { it.value.timesCorrect }
                            val masteredSet = civicsState.progressMap.filterValues { it.isMastered || it.timesCorrect >= 3 }.keys
                            examViewModel.startSimulation(
                                questionCount = selectedQuestionCount,
                                format = selectedExamFormat,
                                blindAudio = isBlindAudioMode,
                                seniorOnly = isSenior6520FilterExam,
                                failedOnly = isFailedOnlyExam,
                                includeIntroPhase = isIncludeIntroPhase,
                                starredIds = starredIds,
                                failedIdsWithCount = failedMap,
                                attemptsCountMap = attemptsMap,
                                correctCountMap = correctMap,
                                masteredIds = masteredSet
                            )
                            currentScreen = Screen.Simulation
                        },
                        onGoHome = { currentScreen = Screen.Home }
                    )
                }
            }
        }
    }

    // Start Simulation Configuration Dialog
    if (showStartExamDialog) {
        AlertDialog(
            onDismissRequest = { showStartExamDialog = false },
            title = {
                Text(
                    text = if (lang == AppLanguage.SPANISH) "Configurar Examen de Ciudadanía" else "Configure Civics Self-Test",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
            },
            text = {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    // Audio-First Blind Sound Setting
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Hearing,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(22.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = if (lang == AppLanguage.SPANISH) "Audio-First (Sin Letras Primero)" else "Audio-First (Sound Only)",
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                                    )
                                    Text(
                                        text = if (lang == AppLanguage.SPANISH) "El audio suena primero sin texto para entrenar tu oído." else "Sound plays first without text to train your ear.",
                                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp)
                                    )
                                }
                            }
                            Switch(
                                checked = isBlindAudioMode,
                                onCheckedChange = { isBlindAudioMode = it }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Include Part 1 N-400 Personal Questions Toggle
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PersonPin,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.size(22.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = if (lang == AppLanguage.SPANISH) "Incluir Preguntas N-400 Iniciales" else "Include N-400 Warm-Up",
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                                    )
                                    Text(
                                        text = if (lang == AppLanguage.SPANISH) "Nombre, edad, padres y juramento de inicio" else "Name, age, parents & oath intro questions",
                                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp)
                                    )
                                }
                            }
                            Switch(
                                checked = isIncludeIntroPhase,
                                onCheckedChange = { isIncludeIntroPhase = it }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Notice: Always all 100 questions, sorted least answered and most failed first
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Hearing,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (lang == AppLanguage.SPANISH)
                                    "Todas las preguntas oficiales (orden: menos practicadas y más falladas primero)"
                                else
                                    "All official questions (order: least answered and most missed first)",
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = if (lang == AppLanguage.SPANISH) "Formato del examen:" else "Test format:",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedExamFormat = ExamFormat.MULTIPLE_CHOICE }
                    ) {
                        RadioButton(
                            selected = selectedExamFormat == ExamFormat.MULTIPLE_CHOICE,
                            onClick = { selectedExamFormat = ExamFormat.MULTIPLE_CHOICE }
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Column {
                            Text(
                                text = if (lang == AppLanguage.SPANISH) "Opción Múltiple (Recomendado)" else "Multiple Choice",
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = if (lang == AppLanguage.SPANISH) "Escuchas el sonido y seleccionas entre 4 opciones en inglés" else "Listen to sound, read 4 options in English and tap",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedExamFormat = ExamFormat.ORAL_INTERVIEW }
                    ) {
                        RadioButton(
                            selected = selectedExamFormat == ExamFormat.ORAL_INTERVIEW,
                            onClick = { selectedExamFormat = ExamFormat.ORAL_INTERVIEW }
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Column {
                            Text(
                                text = if (lang == AppLanguage.SPANISH) "Simulación Oral del Oficial" else "Officer Oral Simulation",
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = if (lang == AppLanguage.SPANISH) "Escuchas al oficial, respondes en voz alta y te autoevalúas" else "Listen to sound, answer aloud, reveal official answers",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showStartExamDialog = false
                        val qualifiesSenior = userProfile.qualifiesForSenior6520
                        val attemptsMap = civicsState.progressMap.mapValues { it.value.timesCorrect + it.value.timesIncorrect }
                        val correctMap = civicsState.progressMap.mapValues { it.value.timesCorrect }
                        val masteredSet = civicsState.progressMap.filterValues { it.isMastered || it.timesCorrect >= 3 }.keys
                        examViewModel.startSimulation(
                            questionCount = if (qualifiesSenior) 20 else 100,
                            format = selectedExamFormat,
                            blindAudio = isBlindAudioMode,
                            seniorOnly = qualifiesSenior,
                            failedOnly = false,
                            includeIntroPhase = isIncludeIntroPhase,
                            starredIds = starredIds,
                            failedIdsWithCount = failedMap,
                            attemptsCountMap = attemptsMap,
                            correctCountMap = correctMap,
                            masteredIds = masteredSet
                        )
                        currentScreen = Screen.Simulation
                    },
                    modifier = Modifier.testTag("dialog_start_btn")
                ) {
                    val qualifiesSenior = userProfile.qualifiesForSenior6520
                    val qCount = if (qualifiesSenior) 20 else 100
                    Text(if (lang == AppLanguage.SPANISH) "Comenzar Examen ($qCount Qs)" else "Begin Test ($qCount Qs)")
                }
            },
            dismissButton = {
                TextButton(onClick = { showStartExamDialog = false }) {
                    Text(if (lang == AppLanguage.SPANISH) "Cancelar" else "Cancel")
                }
            }
        )
    }

    // Reset Progress Confirmation Dialog
    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = {
                Text(if (lang == AppLanguage.SPANISH) "¿Reiniciar Progreso de Estudio?" else "Reset Study Progress?")
            },
            text = {
                Text(
                    if (lang == AppLanguage.SPANISH)
                        "Esto borrará tus preguntas favoritas, preguntas dominadas y el historial de exámenes."
                    else
                        "This will clear your starred questions, mastered records, and test attempt history."
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showResetDialog = false
                        civicsViewModel.resetProgress()
                    },
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = RedSecondary)
                ) {
                    Text(if (lang == AppLanguage.SPANISH) "Reiniciar Todo" else "Reset All")
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text(if (lang == AppLanguage.SPANISH) "Cancelar" else "Cancel")
                }
            }
        )
    }
}
