package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Hearing
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.util.AVAILABLE_OFFICER_VOICES
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.ui.components.EnglishSpeechInput
import com.example.ui.theme.RedSecondary
import com.example.ui.theme.StarGold
import com.example.ui.theme.SuccessGreen
import com.example.ui.util.AppStrings
import com.example.ui.viewmodel.ExamFormat
import com.example.ui.viewmodel.ExamSimulationViewModel

@Composable
fun ExamSimulationScreen(
    viewModel: ExamSimulationViewModel,
    starredIds: Set<Int>,
    onFinishExam: () -> Unit,
    onCancelExam: () -> Unit,
    modifier: Modifier = Modifier,
    language: AppLanguage = AppLanguage.SPANISH
) {
    val state by viewModel.state.collectAsState()
    val isSpeaking by viewModel.ttsManager.isSpeaking.collectAsState()
    var isOfficerMenuExpanded by remember { mutableStateOf(false) }

    BackHandler {
        viewModel.stopAudio()
        onCancelExam()
    }

    if (state.isFinished) {
        onFinishExam()
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("simulation_screen")
    ) {
        // Top Header Bar: Progress, Score, Officer Dropdown & Exit Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (state.isIntroPhaseActive)
                        "Part 1: Personal Interview (${state.currentIntroIndex + 1}/${state.introItems.size})"
                    else
                        "Civics Question ${state.currentIndex + 1} of ${state.totalQuestions}",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = if (state.isIntroPhaseActive) "N-400 Personal Assessment" else "Score: ${state.score} (Need 6 to pass)",
                    style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                // Officer Dropdown Menu Trigger
                Box {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier
                            .clickable { isOfficerMenuExpanded = true }
                            .testTag("simulation_voice_switcher")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.RecordVoiceOver,
                                contentDescription = "Switch Voice",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Column {
                                Text(
                                    text = state.currentOfficer.displayName,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                )
                                Text(
                                    text = "Officers ▼",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 9.sp,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                )
                            }
                        }
                    }

                    DropdownMenu(
                        expanded = isOfficerMenuExpanded,
                        onDismissRequest = { isOfficerMenuExpanded = false },
                        modifier = Modifier.testTag("simulation_officers_menu")
                    ) {
                        AVAILABLE_OFFICER_VOICES.forEach { officer ->
                            val isSelected = officer.id == state.currentOfficer.id
                            DropdownMenuItem(
                                text = {
                                    Column {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = officer.displayName,
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                            )
                                            if (isSelected) {
                                                Icon(
                                                    imageVector = Icons.Default.Check,
                                                    contentDescription = null,
                                                    tint = MaterialTheme.colorScheme.primary,
                                                    modifier = Modifier.size(16.dp)
                                                )
                                            }
                                        }
                                        Text(
                                            text = officer.description,
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                fontSize = 10.sp,
                                                color = MaterialTheme.colorScheme.outline
                                            )
                                        )
                                    }
                                },
                                onClick = {
                                    viewModel.selectOfficerVoice(officer)
                                    isOfficerMenuExpanded = false
                                }
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LinearProgressIndicator(
            progress = { state.progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(14.dp))

        // PHASE 1: N-400 INTRODUCTORY QUESTIONS (If enabled)
        if (state.isIntroPhaseActive) {
            val introItem = state.currentIntroItem
            if (introItem != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant
                                ) {
                                    Text(
                                        text = "N-400 PRELIMINARY",
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                                    )
                                }

                                FilledTonalButton(
                                    onClick = { viewModel.playCurrentQuestionAudio() },
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Icon(
                                        imageVector = if (isSpeaking) Icons.Default.GraphicEq else Icons.Default.Replay,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Repeat Sound")
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Blind sound card (clickable to replay audio)
                            if (!introItem.isTextRevealed) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f))
                                        .clickable { viewModel.playCurrentQuestionAudio() }
                                        .padding(24.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(
                                            imageVector = Icons.Default.Hearing,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(44.dp)
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = AppStrings.soundOnlyTitle(language),
                                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                            textAlign = TextAlign.Center
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = AppStrings.soundOnlySubtitle(language),
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                textAlign = TextAlign.Center,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        )
                                    }
                                }
                            } else {
                                Text(
                                    text = introItem.question.questionEn,
                                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = introItem.question.questionEs,
                                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                                )
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            OutlinedButton(
                                onClick = { viewModel.toggleRevealQuestionText() },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(
                                    imageVector = if (introItem.isTextRevealed) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(if (introItem.isTextRevealed) "Hide Text" else "Reveal Question Text (Leer texto)")
                            }

                            if (introItem.isAnswerRevealed) {
                                Spacer(modifier = Modifier.height(14.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
                                        .padding(12.dp)
                                ) {
                                    Column {
                                        Text(
                                            text = "Sample Answer: \"${introItem.question.sampleAnswersEn.first()}\"",
                                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                                        )
                                        Text(
                                            text = "Tip: ${introItem.question.officerTip}",
                                            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        )
                                    }
                                }
                            }
                        }

                        // Intro bottom action
                        if (!introItem.isAnswerRevealed) {
                            Button(
                                onClick = { viewModel.revealOralAnswer() },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Check Recommended Answer")
                            }
                        } else {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                OutlinedButton(
                                    onClick = { viewModel.gradeIntroAnswer(false) },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(48.dp),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Icon(Icons.Default.Close, contentDescription = null, tint = RedSecondary)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Missed It", color = RedSecondary)
                                }

                                Button(
                                    onClick = { viewModel.gradeIntroAnswer(true) },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(48.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen)
                                ) {
                                    Icon(Icons.Default.Check, contentDescription = null)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Understood!")
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // PHASE 2: CIVICS 100 QUESTIONS
            val item = state.currentItem
            if (item != null) {
                val q = item.question
                val isStarred = starredIds.contains(q.id)

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            // Top Row in Card: Question Badge, Repeat Sound Button, Star Button
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant
                                ) {
                                    Text(
                                        text = "QUESTION #${q.id}",
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                                    )
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    // Repeat Sound Button (Unlimited repeats)
                                    FilledTonalButton(
                                        onClick = { viewModel.playCurrentQuestionAudio() },
                                        shape = RoundedCornerShape(10.dp),
                                        modifier = Modifier.testTag("repeat_sound_btn")
                                    ) {
                                        Icon(
                                            imageVector = if (isSpeaking) Icons.Default.GraphicEq else Icons.Default.Replay,
                                            contentDescription = null,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Repeat Sound")
                                    }

                                    IconButton(onClick = { viewModel.toggleStar(q.id) }) {
                                        Icon(
                                            imageVector = if (isStarred) Icons.Filled.Star else Icons.Outlined.StarBorder,
                                            contentDescription = "Star",
                                            tint = if (isStarred) StarGold else MaterialTheme.colorScheme.outline
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            // BLIND SOUND-FIRST DISPLAY: Text is hidden by default! (clickable to replay audio)
                            if (!item.isQuestionTextRevealed) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f))
                                        .clickable { viewModel.playCurrentQuestionAudio() }
                                        .padding(20.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(
                                            imageVector = Icons.Default.Hearing,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(40.dp)
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = AppStrings.soundOnlyTitle(language),
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.primary
                                            ),
                                            textAlign = TextAlign.Center
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = AppStrings.soundOnlySubtitle(language),
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                textAlign = TextAlign.Center,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        )
                                    }
                                }
                            } else {
                                // Question Text is revealed
                                Column {
                                    Text(
                                        text = q.questionEn,
                                        style = MaterialTheme.typography.titleLarge.copy(
                                            fontWeight = FontWeight.Bold,
                                            lineHeight = 28.sp
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = q.questionEs,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Button to reveal/hide question text while repeating sound
                            OutlinedButton(
                                onClick = { viewModel.toggleRevealQuestionText() },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("toggle_question_text_btn"),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(
                                    imageVector = if (item.isQuestionTextRevealed) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(if (item.isQuestionTextRevealed) "Hide Question Text" else "Reveal Question Text (Leer texto)")
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Multiple Choice Options List
                            if (state.format == ExamFormat.MULTIPLE_CHOICE) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Read or speak your answer in English:",
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    )
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                // Microphone speech input for answering in English!
                                if (item.selectedAnswer == null) {
                                    EnglishSpeechInput(
                                        language = AppLanguage.ENGLISH,
                                        onSpeechResult = { spoken ->
                                            viewModel.submitSpokenAnswer(spoken)
                                        },
                                        promptMessage = "Speak your answer in English...",
                                        isCompact = true
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                }

                                item.options.forEachIndexed { optIndex, option ->
                                    val isSelected = item.selectedAnswer == option
                                    val isOptionCorrect = q.acceptableAnswersEn.any {
                                        it.equals(option, ignoreCase = true) || option.contains(it, ignoreCase = true)
                                    }

                                    val containerColor = when {
                                        item.selectedAnswer == null -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
                                        isSelected && isOptionCorrect -> SuccessGreen.copy(alpha = 0.2f)
                                        isSelected && !isOptionCorrect -> RedSecondary.copy(alpha = 0.2f)
                                        isOptionCorrect -> SuccessGreen.copy(alpha = 0.2f)
                                        else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f)
                                    }

                                    val borderColor = when {
                                        item.selectedAnswer == null -> MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)
                                        isSelected && isOptionCorrect -> SuccessGreen
                                        isSelected && !isOptionCorrect -> RedSecondary
                                        isOptionCorrect -> SuccessGreen
                                        else -> Color.Transparent
                                    }

                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(containerColor)
                                            .border(1.dp, borderColor, RoundedCornerShape(12.dp))
                                            .clickable(enabled = item.selectedAnswer == null) {
                                                viewModel.selectMultipleChoiceAnswer(option)
                                            }
                                            .padding(12.dp)
                                            .testTag("quiz_option_$optIndex")
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text(
                                                text = "${'A' + optIndex}.",
                                                style = MaterialTheme.typography.titleSmall.copy(
                                                    fontWeight = FontWeight.Bold,
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = option,
                                                style = MaterialTheme.typography.bodyMedium.copy(
                                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                                    color = MaterialTheme.colorScheme.onSurface
                                                ),
                                                modifier = Modifier.weight(1f)
                                            )

                                            // Mini audio button to listen to the option pronunciation
                                            IconButton(
                                                onClick = { viewModel.speakOption(option) },
                                                modifier = Modifier.size(32.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.VolumeUp,
                                                    contentDescription = "Pronounce option",
                                                    tint = MaterialTheme.colorScheme.outline,
                                                    modifier = Modifier.size(16.dp)
                                                )
                                            }

                                            if (item.selectedAnswer != null) {
                                                if (isOptionCorrect) {
                                                    Icon(
                                                        imageVector = Icons.Default.Check,
                                                        contentDescription = "Correct",
                                                        tint = SuccessGreen,
                                                        modifier = Modifier.size(20.dp)
                                                    )
                                                } else if (isSelected) {
                                                    Icon(
                                                        imageVector = Icons.Default.Close,
                                                        contentDescription = "Incorrect",
                                                        tint = RedSecondary,
                                                        modifier = Modifier.size(20.dp)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                // Oral interview mode
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                                        .padding(14.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "🎙️ Answer verbally in English using the microphone below:",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.Medium,
                                            textAlign = TextAlign.Center
                                        )
                                    )

                                    Spacer(modifier = Modifier.height(10.dp))

                                    // English speech input microphone button!
                                    EnglishSpeechInput(
                                        language = AppLanguage.ENGLISH,
                                        onSpeechResult = { spoken ->
                                            viewModel.submitSpokenAnswer(spoken)
                                        },
                                        promptMessage = "Speak your answer in English to the officer...",
                                        isCompact = false
                                    )

                                    if (!item.isAnswerRevealed) {
                                        Spacer(modifier = Modifier.height(10.dp))
                                        OutlinedButton(onClick = { viewModel.revealOralAnswer() }) {
                                            Text("Reveal All Official Answers")
                                        }
                                    } else {
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Column(modifier = Modifier.fillMaxWidth()) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = "All Official Accepted Answers (${q.acceptableAnswersEn.size}):",
                                                    style = MaterialTheme.typography.labelSmall.copy(
                                                        fontWeight = FontWeight.Bold,
                                                        color = MaterialTheme.colorScheme.primary
                                                    )
                                                )

                                                // Reads ALL acceptable answers!
                                                IconButton(
                                                    onClick = {
                                                        viewModel.speak(q.acceptableAnswersEn.joinToString(separator = ", or "))
                                                    },
                                                    modifier = Modifier.size(32.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.VolumeUp,
                                                        contentDescription = "Read all acceptable answers",
                                                        tint = MaterialTheme.colorScheme.primary,
                                                        modifier = Modifier.size(18.dp)
                                                    )
                                                }
                                            }

                                            q.acceptableAnswersEn.forEach { ans ->
                                                Text(
                                                    text = "• $ans",
                                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // Next Question Button / Self-Grading
                        if (state.format == ExamFormat.MULTIPLE_CHOICE) {
                            AnimatedVisibility(visible = item.selectedAnswer != null) {
                                Button(
                                    onClick = { viewModel.nextQuestion() },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp)
                                        .testTag("next_question_btn"),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text(
                                        text = if (state.currentIndex + 1 < state.totalQuestions) "Next Question" else "See Final Results",
                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                                }
                            }
                        } else if (item.isAnswerRevealed) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                OutlinedButton(
                                    onClick = { viewModel.gradeOralAnswer(false) },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(48.dp),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Icon(Icons.Default.Close, contentDescription = null, tint = RedSecondary)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("I Missed It", color = RedSecondary)
                                }

                                Button(
                                    onClick = { viewModel.gradeOralAnswer(true) },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(48.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen)
                                ) {
                                    Icon(Icons.Default.Check, contentDescription = null)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Got It Right!")
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Exit Test Link
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Exit Test",
                style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.outline),
                modifier = Modifier
                    .clickable { onCancelExam() }
                    .padding(8.dp)
                    .testTag("exit_exam_button")
            )
        }
    }
}
