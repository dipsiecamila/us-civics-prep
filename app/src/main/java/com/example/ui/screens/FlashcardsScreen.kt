package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.FilterChip
import com.example.data.model.AppLanguage
import com.example.ui.theme.RedSecondary
import com.example.ui.theme.StarGold
import com.example.ui.theme.SuccessGreen
import com.example.ui.util.AppStrings
import com.example.ui.components.bounceClick
import com.example.ui.viewmodel.FlashcardOrderMode
import com.example.ui.viewmodel.FlashcardsViewModel

@Composable
fun FlashcardsScreen(
    viewModel: FlashcardsViewModel,
    starredIds: Set<Int>,
    language: AppLanguage = AppLanguage.SPANISH,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()
    val currentQ = state.currentQuestion
    val isStarred = currentQ?.let { starredIds.contains(it.id) } == true

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("flashcards_screen"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Deck Top Bar: Progress, Shuffle, Star
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Text(
                    text = state.progressText,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = { viewModel.toggleSpanish() },
                    modifier = Modifier.testTag("flashcard_toggle_spanish")
                ) {
                    Icon(
                        imageVector = Icons.Default.Translate,
                        contentDescription = "Toggle Spanish",
                        tint = if (state.showSpanishTranslation) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                    )
                }

                IconButton(
                    onClick = { viewModel.shuffleDeck() },
                    modifier = Modifier.testTag("flashcard_shuffle_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Shuffle,
                        contentDescription = "Shuffle cards",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                currentQ?.let { q ->
                    IconButton(
                        onClick = { viewModel.toggleStar(q.id) },
                        modifier = Modifier.testTag("flashcard_star_button")
                    ) {
                        Icon(
                            imageVector = if (isStarred) Icons.Filled.Star else Icons.Outlined.StarBorder,
                            contentDescription = "Star card",
                            tint = if (isStarred) StarGold else MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Order Mode Selector: Prioridad (Sin ver / Errores), Aleatorio, Orden oficial (1-100)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            FilterChip(
                selected = state.orderMode == FlashcardOrderMode.PRIORITY,
                onClick = { viewModel.setOrderMode(FlashcardOrderMode.PRIORITY) },
                label = { Text(AppStrings.flashcardOrderPriority(language), fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                modifier = Modifier.weight(1f).testTag("flashcard_order_priority")
            )
            FilterChip(
                selected = state.orderMode == FlashcardOrderMode.SHUFFLED,
                onClick = { viewModel.setOrderMode(FlashcardOrderMode.SHUFFLED) },
                label = { Text(AppStrings.flashcardOrderShuffle(language), fontSize = 11.sp) },
                modifier = Modifier.weight(1f).testTag("flashcard_order_shuffle")
            )
            FilterChip(
                selected = state.orderMode == FlashcardOrderMode.NUMERICAL,
                onClick = { viewModel.setOrderMode(FlashcardOrderMode.NUMERICAL) },
                label = { Text("1 - 100", fontSize = 11.sp) },
                modifier = Modifier.weight(1f).testTag("flashcard_order_numerical")
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Interactive Flashcard Box
        if (currentQ != null) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .bounceClick()
                    .clickable { viewModel.flipCard() }
                    .testTag("flashcard_flip_card"),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (state.isFlipped)
                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f)
                    else
                        MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Card Header
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.primaryContainer
                            ) {
                                Text(
                                    text = "Question #${currentQ.id}",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                )
                            }

                            Text(
                                text = if (state.isFlipped) AppStrings.flashcardOfficialAnswer(language) else AppStrings.flashcardTapToFlip(language),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            )

                            IconButton(
                                onClick = {
                                    if (state.isFlipped) {
                                        viewModel.speak(currentQ.acceptableAnswersEn.joinToString(separator = ", or "))
                                    } else {
                                        viewModel.speak(currentQ.questionEn)
                                    }
                                },
                                modifier = Modifier.size(36.dp).bounceClick()
                            ) {
                                Icon(
                                    imageVector = Icons.Default.VolumeUp,
                                    contentDescription = "Pronounce",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Front or Back Content
                        if (!state.isFlipped) {
                            // FRONT: Question
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = currentQ.questionEn,
                                    style = MaterialTheme.typography.headlineSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center,
                                        lineHeight = 32.sp
                                    )
                                )

                                if (state.showSpanishTranslation) {
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        text = currentQ.questionEs,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            textAlign = TextAlign.Center
                                        )
                                    )
                                }
                            }
                        } else {
                            // BACK: Acceptable Answers
                            Column(
                                horizontalAlignment = Alignment.Start,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Acceptable Answers:",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                currentQ.acceptableAnswersEn.forEachIndexed { index, ans ->
                                    Row(
                                        modifier = Modifier.padding(vertical = 4.dp),
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .padding(top = 8.dp, end = 10.dp)
                                                .size(6.dp)
                                                .background(MaterialTheme.colorScheme.primary, CircleShape)
                                        )
                                        Column {
                                            Text(
                                                text = ans,
                                                style = MaterialTheme.typography.titleMedium.copy(
                                                    fontWeight = FontWeight.SemiBold
                                                )
                                            )
                                            if (state.showSpanishTranslation && index < currentQ.acceptableAnswersEs.size) {
                                                Text(
                                                    text = currentQ.acceptableAnswersEs[index],
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

                        Spacer(modifier = Modifier.height(24.dp))

                        // Hint pill
                        Text(
                            text = if (state.isFlipped) "Tap card to flip back to question" else "Tap card to reveal official answer",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 11.sp
                            )
                        )
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text("No questions in this filter")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mastery Self-Assessment Buttons (saves answer directly to local database!)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = {
                    currentQ?.let { viewModel.recordResult(it.id, false) }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .testTag("flashcard_still_learning_btn"),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = RedSecondary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = AppStrings.flashcardStillLearning(language),
                    style = MaterialTheme.typography.labelLarge.copy(color = RedSecondary)
                )
            }

            Button(
                onClick = {
                    currentQ?.let { viewModel.recordResult(it.id, true) }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .testTag("flashcard_know_it_btn"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SuccessGreen,
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = AppStrings.flashcardKnowIt(language),
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Navigation Controls (Previous / Next)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FilledTonalButton(
                onClick = { viewModel.prevCard() },
                enabled = state.currentIndex > 0,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                modifier = Modifier.bounceClick().testTag("flashcard_prev_btn")
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Previous")
                Spacer(modifier = Modifier.width(4.dp))
                Text(AppStrings.navPrevious(language))
            }

            FilledTonalButton(
                onClick = { viewModel.nextCard() },
                enabled = state.currentIndex + 1 < state.questions.size,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                modifier = Modifier.bounceClick().testTag("flashcard_next_btn")
            ) {
                Text(AppStrings.navNext(language))
                Spacer(modifier = Modifier.width(4.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next")
            }
        }
    }
}
