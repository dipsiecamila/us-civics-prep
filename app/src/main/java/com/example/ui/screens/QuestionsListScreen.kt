package com.example.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.model.QuestionCategory
import com.example.ui.components.QuestionCard
import com.example.ui.theme.StarGold
import com.example.ui.viewmodel.CivicsUiState

@Composable
fun QuestionsListScreen(
    uiState: CivicsUiState,
    onSearchQueryChange: (String) -> Unit,
    onSelectCategory: (QuestionCategory?) -> Unit,
    onToggleStarredFilter: () -> Unit,
    onToggleSenior6520Filter: () -> Unit,
    onToggleSpanishHelper: () -> Unit,
    onToggleStar: (Int) -> Unit,
    onToggleMastered: (Int) -> Unit,
    onSpeak: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("questions_list_screen")
    ) {
        // Search and Filter Bar
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = onSearchQueryChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("search_questions_input"),
                placeholder = { Text("Search by question, keyword or #...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                trailingIcon = {
                    if (uiState.searchQuery.isNotEmpty()) {
                        IconButton(onClick = { onSearchQueryChange("") }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear search"
                            )
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Bilingual Spanish Translation Toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Translate,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = " Español (Bilingual helper)",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Medium
                        )
                    )
                }

                Switch(
                    checked = uiState.showSpanishHelper,
                    onCheckedChange = { onToggleSpanishHelper() },
                    modifier = Modifier.testTag("spanish_helper_switch"),
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.primary,
                        checkedTrackColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Horizontal Filter Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = uiState.selectedCategory == null && !uiState.isStarredOnly && !uiState.isSenior6520Only,
                    onClick = {
                        onSelectCategory(null)
                    },
                    label = { Text("All (100)") },
                    modifier = Modifier.testTag("filter_all")
                )

                FilterChip(
                    selected = uiState.isStarredOnly,
                    onClick = onToggleStarredFilter,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = StarGold,
                            modifier = Modifier.size(16.dp)
                        )
                    },
                    label = { Text("Starred (${uiState.starredCount})") },
                    modifier = Modifier.testTag("filter_starred")
                )



                FilterChip(
                    selected = uiState.selectedCategory == QuestionCategory.AMERICAN_GOVERNMENT,
                    onClick = {
                        onSelectCategory(
                            if (uiState.selectedCategory == QuestionCategory.AMERICAN_GOVERNMENT) null
                            else QuestionCategory.AMERICAN_GOVERNMENT
                        )
                    },
                    label = { Text("Government") },
                    modifier = Modifier.testTag("filter_government")
                )

                FilterChip(
                    selected = uiState.selectedCategory == QuestionCategory.AMERICAN_HISTORY,
                    onClick = {
                        onSelectCategory(
                            if (uiState.selectedCategory == QuestionCategory.AMERICAN_HISTORY) null
                            else QuestionCategory.AMERICAN_HISTORY
                        )
                    },
                    label = { Text("History") },
                    modifier = Modifier.testTag("filter_history")
                )

                FilterChip(
                    selected = uiState.selectedCategory == QuestionCategory.INTEGRATED_CIVICS,
                    onClick = {
                        onSelectCategory(
                            if (uiState.selectedCategory == QuestionCategory.INTEGRATED_CIVICS) null
                            else QuestionCategory.INTEGRATED_CIVICS
                        )
                    },
                    label = { Text("Integrated") },
                    modifier = Modifier.testTag("filter_integrated")
                )
            }
        }

        // Questions List Count
        Text(
            text = "Showing ${uiState.filteredQuestions.size} questions",
            style = MaterialTheme.typography.labelSmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )

        // Lazy list of Question Cards
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(uiState.filteredQuestions, key = { it.id }) { question ->
                val progress = uiState.progressMap[question.id]
                QuestionCard(
                    question = question,
                    isStarred = progress?.isStarred == true,
                    isMastered = progress?.isMastered == true,
                    showSpanishHelper = uiState.showSpanishHelper,
                    language = uiState.userProfile.language,
                    onToggleStar = { onToggleStar(question.id) },
                    onToggleMastered = { onToggleMastered(question.id) },
                    onSpeak = onSpeak
                )
            }
        }
    }
}
