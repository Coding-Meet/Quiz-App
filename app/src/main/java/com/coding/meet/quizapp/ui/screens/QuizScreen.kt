package com.coding.meet.quizapp.ui.screens

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.coding.meet.quizapp.model.Question
import com.coding.meet.quizapp.viewmodel.QuizViewModel

@Composable
fun QuizScreen(
    viewModel: QuizViewModel,
    onBackToCategories: () -> Unit
) {
    val quizState by viewModel.quizState.collectAsState()
    
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            quizState.questions.isEmpty() -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(48.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Loading questions...",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            quizState.isQuizComplete -> {
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + expandVertically()
                ) {
                    QuizCompletedScreen(
                        score = quizState.score,
                        totalQuestions = quizState.questions.size,
                        onRestartQuiz = { viewModel.restartQuiz() },
                        onBackToCategories = onBackToCategories
                    )
                }
            }
            else -> {
                val currentQuestion = viewModel.getCurrentQuestion()
                if (currentQuestion != null) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        QuizProgress(
                            currentQuestion = quizState.currentQuestionIndex + 1,
                            totalQuestions = quizState.questions.size
                        )
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        AnimatedContent(
                            targetState = currentQuestion,
                            transitionSpec = {
                                fadeIn(animationSpec = tween(300)) togetherWith
                                        fadeOut(animationSpec = tween(300))
                            }
                        ) { question ->
                            QuizContent(
                                question = question,
                                selectedAnswer = quizState.selectedAnswer,
                                onAnswerSelected = { viewModel.selectAnswer(it) },
                                onNextQuestion = { viewModel.moveToNextQuestion() }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QuizProgress(
    currentQuestion: Int,
    totalQuestions: Int
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Question $currentQuestion of $totalQuestions",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "${(currentQuestion.toFloat() / totalQuestions * 100).toInt()}%",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
        
        LinearProgressIndicator(
            progress = currentQuestion.toFloat() / totalQuestions,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

@Composable
fun QuizContent(
    question: Question,
    selectedAnswer: Int?,
    onAnswerSelected: (Int) -> Unit,
    onNextQuestion: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = question.question,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )
        }

        question.options.forEachIndexed { index, option ->
            OutlinedButton(
                onClick = { onAnswerSelected(index) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .animateContentSize(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (selectedAnswer == index)
                        MaterialTheme.colorScheme.primaryContainer
                    else
                        MaterialTheme.colorScheme.surface
                )
            ) {
                Text(
                    text = option,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (selectedAnswer == index)
                        MaterialTheme.colorScheme.onPrimaryContainer
                    else
                        MaterialTheme.colorScheme.onSurface
                )
            }
        }

        AnimatedVisibility(
            visible = selectedAnswer != null,
            enter = fadeIn() + expandVertically()
        ) {
            Button(
                onClick = onNextQuestion,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = if (question.id == question.options.size - 1) "Finish Quiz" else "Next Question",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
fun QuizCompletedScreen(
    score: Int,
    totalQuestions: Int,
    onRestartQuiz: () -> Unit,
    onBackToCategories: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Quiz Completed!",
            style = MaterialTheme.typography.displaySmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Your Score: $score / $totalQuestions",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        Text(
            text = "Percentage: ${(score.toFloat() / totalQuestions * 100).toInt()}%",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        FilledTonalButton(
            onClick = onRestartQuiz,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                "Try Again",
                style = MaterialTheme.typography.titleMedium
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedButton(
            onClick = onBackToCategories,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                "Choose Another Category",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
} 