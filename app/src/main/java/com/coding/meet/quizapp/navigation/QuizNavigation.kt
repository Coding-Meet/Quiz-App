package com.coding.meet.quizapp.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.coding.meet.quizapp.ui.components.ThemeMode
import com.coding.meet.quizapp.ui.screens.CategoryScreen
import com.coding.meet.quizapp.ui.screens.GetStartedScreen
import com.coding.meet.quizapp.ui.screens.QuizScreen
import com.coding.meet.quizapp.viewmodel.QuizViewModel

sealed class Screen(val route: String) {
    object GetStarted : Screen("get_started")
    object Category : Screen("category")
    object Quiz : Screen("quiz/{categoryId}") {
        fun createRoute(categoryId: Int) = "quiz/$categoryId"
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun QuizNavigation(
    navController: NavHostController,
    viewModel: QuizViewModel,
    currentTheme: ThemeMode,
    onThemeChanged: (ThemeMode) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.GetStarted.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(
            route = Screen.GetStarted.route,
            enterTransition = {
                fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300))
            }
        ) {
            GetStartedScreen(
                currentTheme = currentTheme,
                onThemeChanged = onThemeChanged,
                onGetStarted = {
                    navController.navigate(Screen.Category.route) {
                        popUpTo(Screen.GetStarted.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Screen.Category.route,
            enterTransition = {
                fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300))
            }
        ) {
            CategoryScreen(
                onCategorySelected = { categoryId ->
                    navController.navigate(Screen.Quiz.createRoute(categoryId)) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(
            route = Screen.Quiz.route,
            arguments = listOf(
                navArgument("categoryId") {
                    type = NavType.StringType
                }
            ),
            enterTransition = {
                fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300))
            }
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId")?.toIntOrNull()
            if (categoryId != null) {
                viewModel.loadQuestions(category = categoryId)
            }
            
            QuizScreen(
                viewModel = viewModel,
                onBackToCategories = {
                   navController.navigateUp()
                }
            )
        }
    }
} 