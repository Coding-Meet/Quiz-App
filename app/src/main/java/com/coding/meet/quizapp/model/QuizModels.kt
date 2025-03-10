package com.coding.meet.quizapp.model

enum class Difficulty {
    EASY,
    MEDIUM,
    HARD
}

data class Category(
    val id: Int,
    val name: String,
    val description: String,
    val iconResId: Int
)

data class Question(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctAnswer: Int,
    val difficulty: Difficulty,
    val categoryId: Int,
    val explanation: String = ""
)

data class QuizState(
    val currentQuestionIndex: Int = 0,
    val questions: List<Question> = emptyList(),
    val selectedAnswer: Int? = null,
    val score: Int = 0,
    val isQuizComplete: Boolean = false
) 