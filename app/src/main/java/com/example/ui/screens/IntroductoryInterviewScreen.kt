package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.FamilyRestroom
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Tab
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.filled.KeyboardDoubleArrowDown
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import com.example.data.model.CountryDirectory
import com.example.data.model.CountryCityInfo
import com.example.data.model.StateDirectory
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.KeyboardHide
import androidx.compose.material.icons.filled.Mic
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import com.example.ui.components.CivicsDatePicker
import com.example.ui.components.EnglishSpeechInput
import com.example.ui.components.bounceClick
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.datasource.IntroductoryInterviewDataSource
import com.example.data.model.AppLanguage
import com.example.data.model.N400DialogueGenerator
import com.example.data.model.N400PersonalProfile
import com.example.data.repository.CivicsRepository
import com.example.ui.theme.SuccessGreen
import com.example.ui.util.AppStrings
import com.example.util.AVAILABLE_OFFICER_VOICES
import com.example.util.TtsManager
import kotlinx.coroutines.launch

private fun String.capitalizeWords(): String = this.split(" ").joinToString(" ") { word ->
    word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}

@Composable
fun IntroductoryInterviewScreen(
    repository: CivicsRepository,
    ttsManager: TtsManager,
    language: AppLanguage = AppLanguage.SPANISH,
    onProfileUpdated: () -> Unit = {},
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val hasInitiallyCompleted = remember { repository.hasCompletedInitialN400() }
    var hasCompletedN400 by remember { mutableStateOf(hasInitiallyCompleted) }
    var selectedTab by remember { mutableIntStateOf(0) }
    var userProfile by remember { mutableStateOf(repository.getN400Profile()) }
    var saveMessage by remember { mutableStateOf<String?>(null) }

    // TTS & Voices
    val currentOfficer by ttsManager.currentVoice.collectAsStateWithLifecycle()
    val isSpeaking by ttsManager.isSpeaking.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    // 23 Official Questions Deck State
    val officialQuestions = remember { IntroductoryInterviewDataSource.questions }
    var currentIndex by remember { mutableIntStateOf(0) }
    var isTextRevealed by remember { mutableStateOf(false) }
    var isAnswerRevealed by remember { mutableStateOf(false) }

    BackHandler {
        ttsManager.stop()
        onBack()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("intro_interview_screen")
    ) {
        // Header
        Surface(
            tonalElevation = 2.dp,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = {
                            ttsManager.stop()
                            onBack()
                        },
                        modifier = Modifier.testTag("intro_back_btn")
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = if (language == AppLanguage.SPANISH) "Entrevista Personal N-400" else "N-400 Personal Interview",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = if (language == AppLanguage.SPANISH) "Cuestionario y entrenamiento oral en inglés" else "Questionnaire & English spoken training",
                            style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.outline)
                        )
                    }

                    // Right placeholder to keep title centered
                    Spacer(modifier = Modifier.size(48.dp))
                }

                // 3 Navigation Tabs (Hidden on first launch until user saves their N-400 profile!)
                if (hasCompletedN400) {
                    Spacer(modifier = Modifier.height(8.dp))
                    TabRow(
                        selectedTabIndex = selectedTab,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Tab(
                            selected = selectedTab == 0,
                            onClick = { selectedTab = 0 },
                            text = {
                                Text(
                                    text = AppStrings.n400TabForm(language),
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                                )
                            },
                            modifier = Modifier.testTag("tab_n400_form")
                        )
                        Tab(
                            selected = selectedTab == 1,
                            onClick = { selectedTab = 1 },
                            text = {
                                Text(
                                    text = AppStrings.n400TabDialogues(language),
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                                )
                            },
                            modifier = Modifier.testTag("tab_n400_dialogues")
                        )
                        Tab(
                            selected = selectedTab == 2,
                            onClick = { selectedTab = 2 },
                            text = {
                                Text(
                                    text = AppStrings.n400TabOfficial(language),
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                                )
                            },
                            modifier = Modifier.testTag("tab_n400_official")
                        )
                    }
                }
            }
        }

        // Tab Content
        Box(modifier = Modifier.weight(1f)) {
            when (selectedTab) {
                0 -> {
                    // TAB 1: FORMULARIO ESCRITO EN EL IDIOMA NATIVO DEL USUARIO
                    N400WrittenFormContent(
                        profile = userProfile,
                        language = language,
                        saveMessage = saveMessage,
                        onProfileChange = { updated ->
                            userProfile = updated
                            repository.saveN400Profile(updated)
                            onProfileUpdated()
                        },
                        onSaveAndGoToDialogues = {
                            repository.saveN400Profile(userProfile)
                            repository.setInitialN400Completed(true)
                            hasCompletedN400 = true
                            saveMessage = AppStrings.n400SavedNotice(language)
                            onProfileUpdated()
                            selectedTab = 1
                        }
                    )
                }

                1 -> {
                    // TAB 2: MIS RESPUESTAS HABLADAS EN INGLÉS CON AUDIO
                    N400PersonalDialoguesContent(
                        profile = userProfile,
                        language = language,
                        ttsManager = ttsManager,
                        onGoToForm = { selectedTab = 0 }
                    )
                }

                2 -> {
                    // TAB 3: SIMULACIÓN OFICIAL CON 23 AUDIOS DE PREGUNTAS
                    N400OfficialInterviewContent(
                        repository = repository,
                        ttsManager = ttsManager,
                        language = language,
                        profile = userProfile,
                        questions = officialQuestions,
                        currentIndex = currentIndex,
                        isTextRevealed = isTextRevealed,
                        isAnswerRevealed = isAnswerRevealed,
                        isSpeaking = isSpeaking,
                        onToggleRevealText = { isTextRevealed = !isTextRevealed },
                        onToggleRevealAnswer = { isAnswerRevealed = !isAnswerRevealed },
                        onNext = {
                            if (currentIndex + 1 < officialQuestions.size) {
                                currentIndex++
                                isTextRevealed = false
                                isAnswerRevealed = false
                                ttsManager.speak(officialQuestions[currentIndex].questionEn)
                            }
                        },
                        onPrevious = {
                            if (currentIndex > 0) {
                                currentIndex--
                                isTextRevealed = false
                                isAnswerRevealed = false
                                ttsManager.speak(officialQuestions[currentIndex].questionEn)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun N400WrittenFormContent(
    profile: N400PersonalProfile,
    language: AppLanguage,
    saveMessage: String?,
    onProfileChange: (N400PersonalProfile) -> Unit,
    onSaveAndGoToDialogues: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val hideKeyboardText = when (language) {
        AppLanguage.SPANISH -> "Ocultar teclado"
        AppLanguage.FRENCH -> "Masquer le clavier"
        AppLanguage.CHINESE -> "收起鍵盤"
        AppLanguage.VIETNAMESE -> "Ẩn bàn phím"
        AppLanguage.TAGALOG -> "Itago ang keyboard"
        AppLanguage.ARABIC -> "إخفاء لوحة المفاتيح"
        AppLanguage.PORTUGUESE -> "Ocultar teclado"
        else -> "Hide Keyboard"
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .padding(16.dp)
            .testTag("n400_written_form"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.EditNote,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = AppStrings.n400FormTitle(language),
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = AppStrings.n400FormSubtitle(language),
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    )
                }
            }
        }

        if (saveMessage != null) {
            item {
                Surface(
                    color = SuccessGreen.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SuccessGreen)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = saveMessage,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = SuccessGreen
                            )
                        )
                    }
                }
            }
        }

        // Section 1: Identificación (Preguntas sobre ti)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Person, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "1. ${AppStrings.n400SectionIdentification(language)}",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    OutlinedTextField(
                        value = profile.fullName,
                        onValueChange = { onProfileChange(profile.copy(fullName = it.capitalizeWords())) },
                        label = { Text(AppStrings.n400FullNameLabel(language)) },
                        placeholder = { Text("e.g. Carlos Eduardo Rodriguez") },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                        modifier = Modifier.fillMaxWidth().testTag("n400_input_name")
                    )

                    // Gender Selection: Masculino / Femenino
                    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = if (language == AppLanguage.SPANISH) "Sexo" else "Sex / Gender",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            val isMale = profile.isMale
                            FilterChip(
                                selected = isMale,
                                onClick = { onProfileChange(profile.copy(gender = "Male")) },
                                label = {
                                    Text(
                                        text = if (language == AppLanguage.SPANISH) "👨 Masculino" else "👨 Male",
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                },
                                modifier = Modifier.weight(1f).testTag("gender_male_chip")
                            )
                            FilterChip(
                                selected = !isMale,
                                onClick = { onProfileChange(profile.copy(gender = "Female")) },
                                label = {
                                    Text(
                                        text = if (language == AppLanguage.SPANISH) "👩 Femenino" else "👩 Female",
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                },
                                modifier = Modifier.weight(1f).testTag("gender_female_chip")
                            )
                        }
                    }

                    CivicsDatePicker(
                        label = AppStrings.n400DobLabel(language),
                        dateString = profile.dateOfBirth,
                        onDateChanged = { newDate ->
                            onProfileChange(profile.copy(dateOfBirth = newDate))
                        },
                        language = language,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // 1st: Country of Birth (with Intellisense suggestions)
                    Column(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = profile.countryOfBirth,
                            onValueChange = { newCountry ->
                                val resetCity = if (newCountry.trim().lowercase() != profile.countryOfBirth.trim().lowercase()) "" else profile.cityOfBirth
                                onProfileChange(profile.copy(countryOfBirth = newCountry, cityOfBirth = resetCity))
                            },
                            label = { Text(AppStrings.n400CountryOfBirthLabel(language)) },
                            placeholder = { Text("e.g. Colombia / México / Cuba / Taiwan") },
                            leadingIcon = {
                                Icon(Icons.Default.Public, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                            modifier = Modifier.fillMaxWidth().testTag("n400_input_country")
                        )
                        val countrySuggestions = remember(profile.countryOfBirth, language) {
                            CountryDirectory.searchCountries(profile.countryOfBirth, language).take(6)
                        }
                        if (countrySuggestions.isNotEmpty() && !countrySuggestions.any {
                            val disp = CountryDirectory.getCountryDisplayName(it, language)
                            disp.equals(profile.countryOfBirth.trim(), true) ||
                            it.countryEn.equals(profile.countryOfBirth.trim(), true) ||
                            it.countryEs.equals(profile.countryOfBirth.trim(), true)
                        }) {
                            Spacer(modifier = Modifier.height(4.dp))
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                items(countrySuggestions) { c ->
                                    val displayName = CountryDirectory.getCountryDisplayName(c, language)
                                    SuggestionChip(
                                        onClick = {
                                            // Changing country resets city to the capital or empty so user picks accurately
                                            onProfileChange(profile.copy(
                                                countryOfBirth = displayName,
                                                cityOfBirth = c.capital
                                            ))
                                        },
                                        label = { Text(displayName, fontSize = 11.sp) }
                                    )
                                }
                            }
                        }
                    }

                    // 2nd: City of Birth (filtered by country intellisense)
                    Column(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = profile.cityOfBirth,
                            onValueChange = { onProfileChange(profile.copy(cityOfBirth = it)) },
                            label = { Text(AppStrings.n400CityOfBirthLabel(language)) },
                            placeholder = { Text("e.g. Bogotá / Guadalajara / Taipei / Paris") },
                            leadingIcon = {
                                Icon(Icons.Default.LocationCity, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                            modifier = Modifier.fillMaxWidth().testTag("n400_input_city")
                        )
                        val citySuggestions = remember(profile.countryOfBirth, profile.cityOfBirth) {
                            CountryDirectory.getCitiesForCountry(profile.countryOfBirth, profile.cityOfBirth).take(5)
                        }
                        if (citySuggestions.isNotEmpty() && !citySuggestions.any { it.equals(profile.cityOfBirth.trim(), true) }) {
                            Spacer(modifier = Modifier.height(4.dp))
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                items(citySuggestions) { city ->
                                    SuggestionChip(
                                        onClick = { onProfileChange(profile.copy(cityOfBirth = city)) },
                                        label = { Text(city, fontSize = 11.sp) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Section 2: Familiares
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.FamilyRestroom, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "2. ${AppStrings.n400SectionFamily(language)}",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = AppStrings.n400ParentsCitizenQuestion(language),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.weight(1f)
                        )
                        Switch(
                            checked = profile.isFatherUSCitizen || profile.isMotherUSCitizen,
                            onCheckedChange = { checked ->
                                onProfileChange(profile.copy(isFatherUSCitizen = checked, isMotherUSCitizen = checked))
                            },
                            modifier = Modifier.testTag("n400_switch_parents_citizen")
                        )
                    }

                    OutlinedTextField(
                        value = profile.fatherName,
                        onValueChange = { onProfileChange(profile.copy(fatherName = it.capitalizeWords())) },
                        label = { Text(AppStrings.n400FatherNameLabel(language)) },
                        placeholder = { Text("e.g. Juan Carlos Rodriguez") },
                        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words, imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                        modifier = Modifier.fillMaxWidth().testTag("n400_input_father")
                    )

                    OutlinedTextField(
                        value = profile.motherName,
                        onValueChange = { onProfileChange(profile.copy(motherName = it.capitalizeWords())) },
                        label = { Text(AppStrings.n400MotherNameLabel(language)) },
                        placeholder = { Text("e.g. Maria Elena Gomez") },
                        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words, imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                        modifier = Modifier.fillMaxWidth().testTag("n400_input_mother")
                    )

                    // Marital Status: Clean 2x2 grid without horizontal overflow or vertical scrollbar lines
                    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf("Married", "Single").forEach { status ->
                                FilterChip(
                                    modifier = Modifier.weight(1f),
                                    selected = profile.maritalStatus.equals(status, ignoreCase = true),
                                    onClick = {
                                        val clearSpouse = if (!status.equals("Married", true)) "" else profile.spouseName
                                        onProfileChange(profile.copy(maritalStatus = status, spouseName = clearSpouse))
                                    },
                                    label = {
                                        Text(
                                            text = AppStrings.n400MaritalStatusLabel(status, language),
                                            modifier = Modifier.fillMaxWidth(),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                )
                            }
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf("Divorced", "Widowed").forEach { status ->
                                FilterChip(
                                    modifier = Modifier.weight(1f),
                                    selected = profile.maritalStatus.equals(status, ignoreCase = true),
                                    onClick = {
                                        onProfileChange(profile.copy(maritalStatus = status, spouseName = ""))
                                    },
                                    label = {
                                        Text(
                                            text = AppStrings.n400MaritalStatusLabel(status, language),
                                            modifier = Modifier.fillMaxWidth(),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                )
                            }
                        }
                    }

                    if (profile.maritalStatus.equals("Married", ignoreCase = true)) {
                        OutlinedTextField(
                            value = profile.spouseName,
                            onValueChange = { onProfileChange(profile.copy(spouseName = it.capitalizeWords())) },
                            label = { Text(AppStrings.n400SpouseNameLabel(language)) },
                            placeholder = { Text("Elena Gomez") },
                            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words, imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                            modifier = Modifier.fillMaxWidth().testTag("n400_input_spouse")
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = AppStrings.n400IsSpouseCitizenQuestion(language),
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.weight(1f)
                            )
                            Switch(
                                checked = profile.isSpouseUSCitizen,
                                onCheckedChange = { checked ->
                                    onProfileChange(profile.copy(isSpouseUSCitizen = checked))
                                },
                                modifier = Modifier.testTag("n400_switch_spouse_citizen")
                            )
                        }
                    }

                    OutlinedTextField(
                        value = profile.numberOfChildren,
                        onValueChange = { input ->
                            val digits = input.filter { it.isDigit() }
                            val newCount = digits.toIntOrNull() ?: 0
                            val existingNames = profile.childrenNames.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                            val updatedNames = if (newCount == 0) "" else existingNames.take(newCount).joinToString(", ")
                            onProfileChange(profile.copy(numberOfChildren = digits, childrenNames = updatedNames))
                        },
                        label = { Text(AppStrings.n400ChildrenCountLabel(language)) },
                        placeholder = { Text("0") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                        modifier = Modifier.fillMaxWidth().testTag("n400_input_children_count")
                    )

                    val childrenCount = profile.numberOfChildren.toIntOrNull() ?: 0
                    if (childrenCount > 0) {
                        Text(
                            text = AppStrings.childrenNamesHeader(childrenCount, language),
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier.padding(top = 4.dp)
                        )

                        val currentParts = profile.childrenNames.split(",").map { it.trim() }
                        for (i in 0 until childrenCount) {
                            val childNumber = i + 1
                            val childCurrentName = currentParts.getOrElse(i) { "" }
                            OutlinedTextField(
                                value = childCurrentName,
                                onValueChange = { typedName ->
                                    val formatted = typedName.capitalizeWords()
                                    val mutableList = (0 until childrenCount).map { idx ->
                                        if (idx == i) formatted else currentParts.getOrElse(idx) { "" }
                                    }.toMutableList()
                                    val joined = mutableList.joinToString(", ")
                                    onProfileChange(profile.copy(childrenNames = joined))
                                },
                                label = { Text(AppStrings.childNameItemLabel(childNumber, language)) },
                                placeholder = { Text("Child #$childNumber") },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(
                                    capitalization = KeyboardCapitalization.Words,
                                    imeAction = if (i == childrenCount - 1) ImeAction.Done else ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(
                                    onNext = { focusManager.moveFocus(FocusDirection.Down) },
                                    onDone = { keyboardController?.hide() }
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("n400_input_child_name_$childNumber")
                            )
                        }
                    }

                }
            }
        }

        // Section 3: Dirección, Empleo y Residencia
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Home, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "3. ${AppStrings.n400CurrentAddressLabel(language)}",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    OutlinedTextField(
                        value = profile.currentAddress,
                        onValueChange = { onProfileChange(profile.copy(currentAddress = it)) },
                        label = { Text(AppStrings.n400CurrentAddressLabel(language)) },
                        placeholder = { Text("123 Main Street, Los Angeles, CA") },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                        modifier = Modifier.fillMaxWidth().testTag("n400_input_address")
                    )

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = profile.occupation,
                            onValueChange = { onProfileChange(profile.copy(occupation = it)) },
                            label = { Text(AppStrings.n400OccupationLabel(language)) },
                            placeholder = { Text("Nurse / Electrician / Engineer") },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Right) }),
                            modifier = Modifier.weight(1f).testTag("n400_input_occupation")
                        )
                        OutlinedTextField(
                            value = profile.employer,
                            onValueChange = { onProfileChange(profile.copy(employer = it)) },
                            label = { Text(AppStrings.n400EmployerLabel(language)) },
                            placeholder = { Text("City Hospital / Company") },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                            modifier = Modifier.weight(1f).testTag("n400_input_employer")
                        )
                    }

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = profile.yearsAsPermanentResident,
                            onValueChange = { onProfileChange(profile.copy(yearsAsPermanentResident = it)) },
                            label = { Text(AppStrings.n400LprYearsLabel(language)) },
                            placeholder = { Text("5") },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Right) }),
                            modifier = Modifier.weight(1f).testTag("n400_input_lpr_years")
                        )
                        OutlinedTextField(
                            value = profile.tripsOutsideUS,
                            onValueChange = { onProfileChange(profile.copy(tripsOutsideUS = it)) },
                            label = { Text(AppStrings.n400TripsLabel(language)) },
                            placeholder = { Text("1 trip for 15 days") },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                            modifier = Modifier.weight(1.5f).testTag("n400_input_trips")
                        )
                    }
                }
            }
        }

        // Section 4: Estado de Residencia en EE. UU. y Examen Cívico
        item {
            var stateExpanded by remember { mutableStateOf(false) }
            val currentState = remember(profile.residenceStateCode) {
                StateDirectory.getState(profile.residenceStateCode) ?: StateDirectory.states.first()
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationCity, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "4. ${AppStrings.n400SectionStateAndCivics(language)}",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    Text(
                        text = AppStrings.n400ResidenceStateLabel(language),
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                    )

                    // State Dropdown selector
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { stateExpanded = true }
                                .testTag("n400_state_selector"),
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 14.dp, vertical = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "🇺🇸",
                                        fontSize = 20.sp
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(
                                            text = "${currentState.name} (${currentState.code})",
                                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                                        )
                                        Text(
                                            text = "Capital: ${currentState.capital} • Gov: ${currentState.governor}",
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                fontSize = 11.sp
                                            )
                                        )
                                    }
                                }
                                Icon(
                                    Icons.Default.KeyboardDoubleArrowDown,
                                    contentDescription = "Select State",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        DropdownMenu(
                            expanded = stateExpanded,
                            onDismissRequest = { stateExpanded = false },
                            modifier = Modifier.fillMaxWidth(0.9f)
                        ) {
                            StateDirectory.states.forEach { st ->
                                DropdownMenuItem(
                                    text = {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text("🇺🇸", fontSize = 16.sp)
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text("${st.name} (${st.code}) - ${st.capital}")
                                        }
                                    },
                                    onClick = {
                                        onProfileChange(profile.copy(residenceStateCode = st.code))
                                        stateExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    // Confirmation of standard 100 or special 65/20 civics questions active
                    val qualifies6520 = profile.calculatedAge >= 65 && (profile.yearsAsPermanentResident.filter { it.isDigit() }.toIntOrNull() ?: 0) >= 20

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f)
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = SuccessGreen,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = if (qualifies6520) AppStrings.civicsExamActive6520Title(language) else AppStrings.civicsExamActiveTitle(language),
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = if (qualifies6520) AppStrings.civicsExamActive6520Subtitle(currentState.name, language) else AppStrings.civicsExamActiveSubtitle(currentState.name, language),
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        // Action Button
        item {
            Button(
                onClick = onSaveAndGoToDialogues,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("n400_save_button"),
                shape = RoundedCornerShape(14.dp)
            ) {
                Icon(Icons.Default.Save, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = AppStrings.n400SaveButton(language),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}

@Composable
private fun N400PersonalDialoguesContent(
    profile: N400PersonalProfile,
    language: AppLanguage,
    ttsManager: TtsManager,
    onGoToForm: () -> Unit
) {
    val isEs = language == AppLanguage.SPANISH
    val dialogues = remember(profile, language) {
        N400DialogueGenerator.generate(profile, language)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("n400_dialogues_list"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f))
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = com.example.ui.util.AppStrings.spokenPracticeHeader(language),
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = if (isEs)
                                "Escucha la pregunta del oficial en inglés y reproduce cómo debes responder exactamente con tus datos."
                            else
                                "Listen to the officer ask in English, then listen to your exact personalized spoken response.",
                            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                        )
                    }
                    OutlinedButton(
                        onClick = onGoToForm,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(if (isEs) "Editar Datos" else "Edit Data")
                    }
                }
            }
        }

        // Downward scroll cue banner inducing user to explore all practice dialogues
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardDoubleArrowDown,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = AppStrings.oralPracticeScrollCue(language),
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onTertiaryContainer,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        }

        // Single English speech input button under the fixed menu
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.45f)
                )
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = if (isEs) "Responder con Voz en Inglés" else "Speak Answer in English",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (isEs) "Toca el micrófono para practicar tu pronunciación en inglés:" else "Tap the microphone to practice speaking in English:",
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    EnglishSpeechInput(
                        language = language,
                        onSpeechResult = { /* user practiced speaking */ },
                        promptMessage = "Speak your response in English...",
                        isCompact = false
                    )
                }
            }
        }

        items(dialogues, key = { it.id }) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("dialogue_card_${item.id}"),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Category & Question in user language (clean, without dlg_ prefix)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                        ) {
                            Text(
                                text = item.category,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                ),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }

                        Text(
                            text = item.questionInUserLang,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.outline,
                                fontSize = 11.sp
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Officer Question Box - Gentle light pad, clickable anywhere to speak!
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.28f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .bounceClick()
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { ttsManager.speak(item.questionEn) }
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "OFFICER ASKS:",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                )
                                Text(
                                    text = item.questionEn,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                )
                            }
                            IconButton(
                                onClick = { ttsManager.speak(item.questionEn) },
                                modifier = Modifier.testTag("play_officer_q_${item.id}")
                            ) {
                                Icon(Icons.Default.VolumeUp, contentDescription = "Play Officer Question", tint = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // User Spoken Answer Box - Gentle light pad, clickable anywhere to speak!
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.22f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .bounceClick()
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { ttsManager.speakUserAnswer(item.answerEn, profile.isMale) }
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = if (isEs) "TU RESPUESTA EN INGLÉS:" else "YOUR SPOKEN ANSWER:",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                )
                                Text(
                                    text = item.answerEn,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                )
                            }
                            IconButton(
                                onClick = { ttsManager.speakUserAnswer(item.answerEn, profile.isMale) },
                                modifier = Modifier.testTag("play_user_ans_${item.id}")
                            ) {
                                Icon(Icons.Default.VolumeUp, contentDescription = "Listen to your answer", tint = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Phonetic Guidance Tip
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = item.spokenTip,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.outline
                            )
                        )
                    }
                }
            }
        }
    }
}

private fun resolveOfficialSampleAnswer(
    qId: Int,
    rawAnswer: String,
    profile: N400PersonalProfile
): String {
    val resolvedList = IntroductoryInterviewDataSource.resolvePersonalSampleAnswersEn(qId, profile)
    val match = resolvedList.firstOrNull()
    if (!match.isNullOrBlank() && !match.contains("[")) return match
    return rawAnswer
        .replace("[Your Full Name]", profile.fullName.ifBlank { "Carlos Eduardo Rodriguez" })
        .replace("[Month] [Day], [Year]", profile.dateOfBirth.ifBlank { "March 15, 1985" })
        .replace("[Age]", profile.calculatedAge.toString())
        .replace("[Your Country of Birth]", profile.countryOfBirth.ifBlank { "Colombia" })
        .replace("[Street Number, Street Name, City, State, Zip Code]", profile.currentAddress.ifBlank { "123 Main Street, Los Angeles, CA" })
        .replace("[Number]", profile.yearsAtCurrentAddress.ifBlank { "5" })
        .replace("[Name]", profile.spouseName.ifBlank { "Elena Gomez" })
        .replace("[Job Title]", profile.occupation.ifBlank { "electrician" })
        .replace("[Company Name]", profile.employer.ifBlank { "City Services" })
}

@Composable
private fun N400OfficialInterviewContent(
    repository: CivicsRepository,
    ttsManager: TtsManager,
    language: AppLanguage,
    profile: N400PersonalProfile,
    questions: List<com.example.data.model.IntroductoryQuestion>,
    currentIndex: Int,
    isTextRevealed: Boolean,
    isAnswerRevealed: Boolean,
    isSpeaking: Boolean,
    onToggleRevealText: () -> Unit,
    onToggleRevealAnswer: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit
) {
    val currentQ = questions.getOrNull(currentIndex) ?: return
    val progress = (currentIndex + 1).toFloat() / questions.size
    val isEs = language == AppLanguage.SPANISH
    val isLastQ = currentIndex == questions.size - 1

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("n400_official_deck"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
            )
        }

        // Hero Audio Prompt Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                    ) {
                        Text(
                            text = "${currentQ.category.uppercase()} • Pregunta ${currentIndex + 1}/${questions.size}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Pulse Speaker Button with bounce click
                    Surface(
                        shape = CircleShape,
                        color = if (isSpeaking) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier
                            .size(76.dp)
                            .bounceClick()
                            .clickable { ttsManager.speak(currentQ.questionEn) }
                            .testTag("intro_repeat_sound_btn")
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.VolumeUp,
                                contentDescription = "Play Question Sound",
                                tint = if (isSpeaking) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Escucha la voz del oficial en inglés",
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.outline)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Blind Mode: Text hidden by default, reveal toggle
                    if (!isTextRevealed) {
                        OutlinedButton(
                            onClick = onToggleRevealText,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.bounceClick().testTag("intro_reveal_text_btn")
                        ) {
                            Icon(Icons.Default.Visibility, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(if (isEs) "Revelar Pregunta Escrita" else "Reveal Written Question")
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .bounceClick()
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { ttsManager.speak(currentQ.questionEn) }
                                .padding(vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = currentQ.questionEn,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center
                                    ),
                                    modifier = Modifier.padding(horizontal = 10.dp)
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = currentQ.questionEs,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.outline,
                                        textAlign = TextAlign.Center
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        // Sample Spoken Answers Card (Auto-filled with user profile data)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = com.example.ui.util.AppStrings.sampleAnswersHeader(language),
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                        IconButton(onClick = onToggleRevealAnswer) {
                            Icon(
                                imageVector = if (isAnswerRevealed) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = "Toggle answer"
                            )
                        }
                    }

                    if (isAnswerRevealed) {
                        Spacer(modifier = Modifier.height(8.dp))
                        currentQ.sampleAnswersEn.forEach { rawAns ->
                            val resolvedAns = resolveOfficialSampleAnswer(currentQ.id, rawAns, profile)
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = MaterialTheme.colorScheme.surface,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .bounceClick()
                                    .clickable { ttsManager.speakUserAnswer(resolvedAns, profile.isMale) }
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "• $resolvedAns",
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                        modifier = Modifier.weight(1f)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Icon(
                                        Icons.Default.VolumeUp,
                                        contentDescription = "Listen",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier
                                            .size(18.dp)
                                            .clickable { ttsManager.speakUserAnswer(resolvedAns, profile.isMale) }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Consejo USCIS: ${currentQ.officerTip}",
                            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.outline)
                        )
                    } else {
                        Text(
                            text = "Toca el ojo para ver y escuchar cómo responder con tus datos.",
                            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.outline)
                        )
                    }
                }
            }
        }

        // Navigation Footer: If on last question (#23), highlight the back button brightly so the applicant knows to exit/review
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (isLastQ) {
                    Button(
                        onClick = onPrevious,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier.bounceClick().testTag("intro_back_highlighted_btn")
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isEs) "← Volver a Pregunta Anterior" else "← Back to Previous Question",
                            fontWeight = FontWeight.Bold
                        )
                    }
                } else {
                    OutlinedButton(
                        onClick = onPrevious,
                        enabled = currentIndex > 0,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.bounceClick()
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(if (isEs) "Anterior" else "Previous")
                    }
                }

                Button(
                    onClick = onNext,
                    enabled = currentIndex + 1 < questions.size,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.bounceClick()
                ) {
                    Text(if (isEs) "Siguiente" else "Next")
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                }
            }
        }
    }
}
