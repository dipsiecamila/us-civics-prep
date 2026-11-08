package com.example.ui.screens

import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Hearing
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.ViewCarousel
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.model.QuestionCategory
import com.example.ui.theme.RedSecondary
import com.example.ui.theme.StarGold
import com.example.ui.theme.SuccessGreen
import com.example.ui.util.AppStrings
import com.example.ui.viewmodel.CivicsUiState

@Composable
fun HomeScreen(
    uiState: CivicsUiState,
    onStartSimulation: (count: Int) -> Unit,
    onStartFailedQuestionsTest: () -> Unit,
    onNavigateQuestions: (category: QuestionCategory?) -> Unit,
    onNavigateIntroInterview: () -> Unit,
    onNavigateAnalytics: () -> Unit,
    onNavigateFlashcards: () -> Unit,
    onNavigateEnglish: () -> Unit,
    onNavigateStateOfficials: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lang = uiState.userProfile.language
    val qualifies6520 = uiState.userProfile.qualifiesForSenior6520

    val progressAnimated by animateFloatAsState(
        targetValue = uiState.readinessPercentage / 100f,
        label = "readiness_anim"
    )

    val failedCount = uiState.progressMap.values.count { it.timesIncorrect > 0 }
    val answeredCount = uiState.progressMap.values.count { it.timesCorrect + it.timesIncorrect > 0 }
    val totalResponsesCount = uiState.progressMap.values.sumOf { it.timesCorrect + it.timesIncorrect }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag("home_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))

            // Hero Banner: Official USCIS Audio Training & Simulation
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.85f),
                                Color(0xFF172554)
                            )
                        )
                    )
                    .padding(20.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.2f)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Hearing,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = AppStrings.heroBadge(lang),
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = AppStrings.heroTitle(lang),
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.ExtraBold,
                                    lineHeight = 30.sp
                                )
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = AppStrings.heroSubtitle(lang),
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color.White.copy(alpha = 0.85f)
                                )
                            )
                        }

                        // Readiness Circular Gauge
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .size(88.dp)
                        ) {
                            CircularProgressIndicator(
                                progress = { progressAnimated },
                                modifier = Modifier.size(88.dp),
                                color = MaterialTheme.colorScheme.tertiary,
                                strokeWidth = 8.dp,
                                trackColor = Color.White.copy(alpha = 0.2f)
                            )
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "${uiState.readinessPercentage}%",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Text(
                                    text = AppStrings.statMastered(lang),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Color.White.copy(alpha = 0.8f),
                                        fontSize = 10.sp
                                    )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Primary Action: Start Simulation (Sound First)
                    Button(
                        onClick = { onStartSimulation(10) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("start_simulation_button"),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = AppStrings.startTestButton(lang),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }



        // Focus Alert / Repetition for Failed Questions
        if (failedCount > 0) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onStartFailedQuestionsTest() }
                        .testTag("failed_questions_banner"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = RedSecondary.copy(alpha = 0.12f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(RedSecondary.copy(alpha = 0.2f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = null,
                                tint = RedSecondary,
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = AppStrings.repeatFailedBannerTitle(failedCount, lang),
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = RedSecondary
                                )
                            )
                            Text(
                                text = AppStrings.repeatFailedBannerSubtitle(lang),
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            tint = RedSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        // Quick Stats: Contestadas, Dominadas, Errores, Favoritas
        item {
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatCard(
                        title = AppStrings.statAnswered(lang),
                        value = "$answeredCount/${uiState.totalQuestions}",
                        icon = Icons.Default.CheckCircle,
                        iconTint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = AppStrings.statMastered(lang),
                        value = "${uiState.masteredCount}/${uiState.totalQuestions}",
                        icon = Icons.Default.Stars,
                        iconTint = SuccessGreen,
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatCard(
                        title = AppStrings.statErrors(lang),
                        value = "$failedCount",
                        icon = Icons.Default.Warning,
                        iconTint = if (failedCount > 0) RedSecondary else SuccessGreen,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = AppStrings.statStarred(lang),
                        value = "${uiState.starredCount}",
                        icon = Icons.Default.Star,
                        iconTint = StarGold,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Training Modes Header
        item {
            Text(
                text = AppStrings.preparationModulesHeader(lang),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        }

        // Module 1: Introductory N-400 Personal Interview
        item {
            TrainingFeatureCard(
                title = AppStrings.moduleIntroTitle(lang),
                subtitle = AppStrings.moduleIntroSubtitle(lang),
                icon = Icons.Default.PersonPin,
                badge = if (lang == AppLanguage.SPANISH) "Inicial" else "Part 1",
                badgeColor = MaterialTheme.colorScheme.secondaryContainer,
                onClick = onNavigateIntroInterview,
                testTag = "card_intro_interview_feature"
            )
        }

        // Module 2: Analytics & Error Charts
        item {
            TrainingFeatureCard(
                title = AppStrings.moduleAnalyticsTitle(lang),
                subtitle = AppStrings.moduleAnalyticsSubtitle(lang),
                icon = Icons.Default.BarChart,
                badge = AppStrings.navAnalytics(lang),
                badgeColor = MaterialTheme.colorScheme.primaryContainer,
                onClick = onNavigateAnalytics,
                testTag = "card_analytics_feature"
            )
        }

        // Module 3: Flashcards
        item {
            TrainingFeatureCard(
                title = AppStrings.moduleFlashcardsTitle(lang),
                subtitle = AppStrings.moduleFlashcardsSubtitle(lang),
                icon = Icons.Default.ViewCarousel,
                badge = "Cards",
                badgeColor = MaterialTheme.colorScheme.surfaceVariant,
                onClick = onNavigateFlashcards,
                testTag = "card_flashcards_feature"
            )
        }

        // Module 4: English Reading & Writing Test
        item {
            TrainingFeatureCard(
                title = AppStrings.moduleEnglishTitle(lang),
                subtitle = AppStrings.moduleEnglishSubtitle(lang),
                icon = Icons.Default.RecordVoiceOver,
                badge = "Part 2 & 3",
                badgeColor = MaterialTheme.colorScheme.tertiaryContainer,
                onClick = onNavigateEnglish,
                testTag = "card_english_feature"
            )
        }



        // Module 6: State Officials
        item {
            TrainingFeatureCard(
                title = AppStrings.moduleStateTitle(lang),
                subtitle = "Gobernador, Senadores y Capital de ${uiState.selectedState.name} (${uiState.selectedState.code})",
                icon = Icons.Default.LocationOn,
                badge = uiState.selectedState.code,
                badgeColor = MaterialTheme.colorScheme.primaryContainer,
                onClick = onNavigateStateOfficials,
                testTag = "card_state_officials_feature"
            )
        }

        // Civics Categories Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = AppStrings.civicsHeaderTitle(lang),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = AppStrings.browseAll(lang),
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier.clickable { onNavigateQuestions(null) }
                )
            }
        }

        item {
            CategoryCard(
                title = AppStrings.catGovTitle(lang),
                count = "47 Questions",
                subtitle = AppStrings.catGovSubtitle(lang),
                icon = Icons.Default.AccountBalance,
                onClick = { onNavigateQuestions(QuestionCategory.AMERICAN_GOVERNMENT) },
                testTag = "cat_government"
            )
        }

        item {
            CategoryCard(
                title = AppStrings.catHistoryTitle(lang),
                count = "30 Questions",
                subtitle = AppStrings.catHistorySubtitle(lang),
                icon = Icons.Default.HistoryEdu,
                onClick = { onNavigateQuestions(QuestionCategory.AMERICAN_HISTORY) },
                testTag = "cat_history"
            )
        }

        item {
            CategoryCard(
                title = AppStrings.catIntegratedTitle(lang),
                count = "23 Questions",
                subtitle = AppStrings.catIntegratedSubtitle(lang),
                icon = Icons.Default.Public,
                onClick = { onNavigateQuestions(QuestionCategory.INTEGRATED_CIVICS) },
                testTag = "cat_integrated"
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    icon: ImageVector,
    iconTint: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
        }
    }
}

@Composable
private fun TrainingFeatureCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    badge: String,
    badgeColor: Color,
    onClick: () -> Unit,
    testTag: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag(testTag),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = badgeColor
                    ) {
                        Text(
                            text = badge,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 10.sp
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                )
            }

            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
private fun CategoryCard(
    title: String,
    count: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit,
    testTag: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag(testTag),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = count,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                )
            }
        }
    }
}
