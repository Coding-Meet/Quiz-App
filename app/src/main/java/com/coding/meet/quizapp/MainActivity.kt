package com.coding.meet.quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.coding.meet.quizapp.navigation.QuizNavigation
import com.coding.meet.quizapp.ui.components.ThemeMode
import com.coding.meet.quizapp.ui.theme.QuizAppTheme
import com.coding.meet.quizapp.viewmodel.QuizViewModel
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var currentTheme by remember { mutableStateOf(ThemeMode.SYSTEM) }
            val isDarkTheme = when (currentTheme) {
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
                ThemeMode.SYSTEM -> isSystemInDarkTheme()
            }

            QuizAppTheme(darkTheme = isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val viewModel: QuizViewModel = viewModel()

                    DisposableEffect(Unit) {
                        onDispose {
                            // Clean up any remaining hover events
                            window.decorView.cancelPendingInputEvents()
                        }
                    }

                    QuizNavigation(
                        navController = navController,
                        viewModel = viewModel,
                        currentTheme = currentTheme,
                        onThemeChanged = { newTheme -> 
                            currentTheme = newTheme
                        }
                    )
                }
            }
        }
    }
}