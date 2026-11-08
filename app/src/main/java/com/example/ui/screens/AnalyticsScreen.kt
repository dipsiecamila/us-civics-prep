package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.ui.graphics.drawscope.Stroke
import com.example.ui.components.bounceClick
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.RedSecondary
import com.example.ui.theme.StarGold
import com.example.ui.theme.SuccessGreen
import com.example.ui.viewmodel.AnalyticsViewModel
import com.example.ui.viewmodel.CivicsQuestionScoreItem
import com.example.ui.viewmodel.IntroQuestionScoreItem
import com.example.ui.viewmodel.StateQuestionScoreItem

@Composable
fun AnalyticsScreen(
    viewModel: AnalyticsViewModel,
    onStartFailedQuestionsTest: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()

    val accuracyAnim by animateFloatAsState(
        targetValue = state.overallAccuracy / 100f,
        label = "accuracy_anim"
    )

    BackHandler {
        onBack()
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("analytics_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.testTag("analytics_back_btn")
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Results & Detailed Analytics",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Personal, State, and 100 Civics ranked from most to least missed",
                        style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    )
                }
            }
        }

        // Summary Hero Card with 3-Color Accuracy Gauge (Hits, Misses, Remaining)
        item {
            val totalExpected = 100
            val correctCount = state.totalCorrect
            val incorrectCount = state.totalIncorrect
            val remainingCount = (totalExpected - (correctCount + incorrectCount)).coerceAtLeast(0)

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "ESTADO DE RESPUESTAS Y PRECISIÓN",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "${state.overallAccuracy}% Acierto",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = if (state.overallAccuracy >= 70) SuccessGreen else MaterialTheme.colorScheme.onSurface
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "✓ $correctCount Buenas",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = SuccessGreen
                                )
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "✗ $incorrectCount Fallos",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = RedSecondary
                                )
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "○ $remainingCount Faltan",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            )
                        }
                    }

                    ThreeColorCircularGauge(
                        correctCount = correctCount,
                        incorrectCount = incorrectCount,
                        remainingCount = remainingCount,
                        accuracyPercent = state.overallAccuracy
                    )
                }
            }
        }

        // Quick Repeat Test Banner for Failed Questions
        if (state.totalIncorrect > 0) {
            item {
                Button(
                    onClick = onStartFailedQuestionsTest,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("analytics_train_failed_btn"),
                    colors = ButtonDefaults.buttonColors(containerColor = RedSecondary),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Train Missed Questions Test (${state.topFailedQuestions.size} Qs)",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }

        // SECTION 1: PREGUNTAS PERSONALES CLASIFICADAS (N-400 Personal Questions)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "1. Preguntas Personales N-400 (Personal Questions)",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Clasificación con puntaje: aciertos y fallos en tu entrevista inicial",
                        style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.outline)
                    )
                }
            }
        }

        items(state.personalQuestions, key = { "personal_${it.question.id}" }) { item ->
            PersonalQuestionScoreRow(
                item = item,
                onSpeak = {
                    val fullAudio = "${item.question.questionEn}. Answer: ${item.question.sampleAnswersEn.joinToString(separator = ", or ")}"
                    viewModel.speak(fullAudio)
                }
            )
        }

        // SECTION 2: PREGUNTAS DE ESTADO (State-Specific Questions)
        item {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "2. Preguntas de Estado (${state.selectedState.name} - ${state.selectedState.code})",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Senadores, Gobernador y Capital con puntaje de práctica",
                        style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.outline)
                    )
                }
            }
        }

        items(state.stateQuestions, key = { "state_${it.question.id}" }) { item ->
            StateQuestionScoreRow(
                item = item,
                onSpeak = {
                    val fullAudio = "${item.question.questionEn}. ${item.stateSpecificAnswer}"
                    viewModel.speak(fullAudio)
                }
            )
        }

        // SECTION 3: LAS 100 PREGUNTAS DE CÍVICA ORDENADAS POR FALLOS
        item {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = RedSecondary,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "3. Las 100 Preguntas de Cívica (Ordenadas por Fallos)",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Primero las que más fallas y de último las que menos (todas con audio completo)",
                        style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.outline)
                    )
                }
            }
        }

        items(state.all100QuestionsRanked, key = { "civics_q_${it.question.id}" }) { item ->
            CivicsQuestionRankedRow(
                item = item,
                onSpeak = {
                    val allAnswersJoined = item.question.acceptableAnswersEn.joinToString(separator = ", or ")
                    val speech = "${item.question.questionEn}. Acceptable answers: $allAnswersJoined"
                    viewModel.speak(speech)
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun ThreeColorCircularGauge(
    correctCount: Int,
    incorrectCount: Int,
    remainingCount: Int,
    accuracyPercent: Int,
    modifier: Modifier = Modifier
) {
    val total = (correctCount + incorrectCount + remainingCount).coerceAtLeast(1)
    val correctSweep = (correctCount.toFloat() / total) * 360f
    val incorrectSweep = (incorrectCount.toFloat() / total) * 360f

    val strokeWidth = 8.dp
    val trackColor = MaterialTheme.colorScheme.surfaceVariant
    val greenColor = SuccessGreen
    val redColor = RedSecondary

    Box(contentAlignment = Alignment.Center, modifier = modifier.size(80.dp)) {
        Canvas(modifier = Modifier.size(76.dp)) {
            val stroke = Stroke(width = strokeWidth.toPx())
            // Neutral track: represents remaining / unanswered questions
            drawArc(
                color = trackColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = stroke
            )
            var currentAngle = -90f
            // 1. Correct arc (Green)
            if (correctSweep > 0f) {
                drawArc(
                    color = greenColor,
                    startAngle = currentAngle,
                    sweepAngle = correctSweep,
                    useCenter = false,
                    style = stroke
                )
                currentAngle += correctSweep
            }
            // 2. Incorrect arc (Red)
            if (incorrectSweep > 0f) {
                drawArc(
                    color = redColor,
                    startAngle = currentAngle,
                    sweepAngle = incorrectSweep,
                    useCenter = false,
                    style = stroke
                )
            }
        }
        Text(
            text = "$accuracyPercent%",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Composable
private fun PersonalQuestionScoreRow(
    item: IntroQuestionScoreItem,
    onSpeak: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .bounceClick()
            .clickable { onSpeak() }
            .testTag("personal_q_${item.question.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Text(
                            text = item.question.category,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    if (item.timesIncorrect > 0) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = RedSecondary.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "${item.timesIncorrect} Miss(es)",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = RedSecondary
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    if (item.timesCorrect > 0) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = SuccessGreen.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "${item.timesCorrect} Hits",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = SuccessGreen
                                )
                            )
                        }
                    } else if (item.timesIncorrect == 0) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Text(
                                text = "Not Tested",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = MaterialTheme.colorScheme.outline
                                )
                            )
                        }
                    }
                }

                IconButton(onClick = onSpeak, modifier = Modifier.size(32.dp)) {
                    Icon(
                        imageVector = Icons.Default.VolumeUp,
                        contentDescription = "Play sound",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = item.question.questionEn,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Sample: \"${item.question.sampleAnswersEn.firstOrNull().orEmpty()}\"",
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
        }
    }
}

@Composable
private fun StateQuestionScoreRow(
    item: StateQuestionScoreItem,
    onSpeak: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .bounceClick()
            .clickable { onSpeak() }
            .testTag("state_q_${item.question.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item.question.questionEn,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(onClick = onSpeak, modifier = Modifier.size(36.dp)) {
                    Icon(
                        imageVector = Icons.Default.VolumeUp,
                        contentDescription = "Play sound",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = item.stateSpecificAnswer,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}

@Composable
private fun CivicsQuestionRankedRow(
    item: CivicsQuestionScoreItem,
    onSpeak: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .bounceClick()
            .clickable { onSpeak() }
            .testTag("ranked_civics_${item.question.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = "#${item.question.id}",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    if (item.timesIncorrect > 0) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = RedSecondary.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "✗ ${item.timesIncorrect} Miss(es)",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = RedSecondary
                                )
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                    }

                    if (item.timesCorrect > 0) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = SuccessGreen.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "✓ ${item.timesCorrect} Hits",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = SuccessGreen
                                )
                            )
                        }
                    } else if (item.timesIncorrect == 0) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                        ) {
                            Text(
                                text = "0 Misses",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.outline)
                            )
                        }
                    }
                }

                IconButton(onClick = onSpeak, modifier = Modifier.size(32.dp)) {
                    Icon(
                        imageVector = Icons.Default.VolumeUp,
                        contentDescription = "Read all answers",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = item.question.questionEn,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "All Answers: ${item.question.acceptableAnswersEn.joinToString(separator = ", or ")}",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}
