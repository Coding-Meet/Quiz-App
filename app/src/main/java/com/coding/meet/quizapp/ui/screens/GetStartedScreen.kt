package com.coding.meet.quizapp.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.coding.meet.quizapp.ui.components.ThemeDialog
import com.coding.meet.quizapp.ui.components.ThemeMode

@Composable
fun GetStartedScreen(
    currentTheme: ThemeMode,
    onThemeChanged: (ThemeMode) -> Unit,
    onGetStarted: () -> Unit
) {
    var showThemeDialog by remember { mutableStateOf(false) }
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Theme button in top-right corner
        IconButton(
            onClick = { showThemeDialog = true },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Change Theme",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        // Main content
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(1000)) +
                    slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(1000)
                    ),
            modifier = Modifier.align(Alignment.Center)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    modifier = Modifier.size(120.dp),
                    tint = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "Quiz App",
                    style = MaterialTheme.typography.displayLarge,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Test your knowledge with interactive questions across various categories",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = onGetStarted,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Start"
                        )
                        Text(
                            text = "Get Started",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
        }
    }

    if (showThemeDialog) {
        ThemeDialog(
            currentTheme = currentTheme,
            onThemeSelected = onThemeChanged,
            onDismiss = { showThemeDialog = false }
        )
    }
} 