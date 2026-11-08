package com.example.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.model.AppLanguage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CivicsTopBar(
    selectedStateCode: String,
    language: AppLanguage,
    ttsManager: com.example.util.TtsManager? = null,
    onLanguageChanged: (AppLanguage) -> Unit,
    onNavigateState: () -> Unit,
    onResetProgress: () -> Unit,
    onNavigateHome: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier.testTag("civics_top_app_bar"),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { onNavigateHome() }
                    .testTag("top_bar_home_logo_title")
            ) {
                Icon(
                    imageVector = Icons.Default.Shield,
                    contentDescription = "US Civics Prep Home",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "US Civics Prep",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        },
        actions = {
            // Officer selector dropdown with cute avatars & voice preview at the very top!
            if (ttsManager != null) {
                OfficerSelectorMenu(
                    ttsManager = ttsManager,
                    language = language
                )
                Spacer(modifier = Modifier.width(4.dp))
            }

            // Language selector dropdown menu (with quick Español/English toggle and 20 languages)
            LanguageSelectorMenu(
                currentLanguage = language,
                onLanguageSelected = onLanguageChanged,
                isCompact = true
            )

            // Reset progress button
            IconButton(
                onClick = onResetProgress,
                modifier = Modifier.testTag("top_bar_reset_btn")
            ) {
                Icon(
                    imageVector = Icons.Default.RestartAlt,
                    contentDescription = "Reset progress",
                    tint = MaterialTheme.colorScheme.outline
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}
