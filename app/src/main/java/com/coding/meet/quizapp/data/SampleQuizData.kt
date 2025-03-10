package com.coding.meet.quizapp.data

import com.coding.meet.quizapp.model.Category
import com.coding.meet.quizapp.model.Difficulty
import com.coding.meet.quizapp.model.Question

object SampleQuizData {
    val categories = listOf(
        Category(
            id = 1,
            name = "Science",
            description = "Test your knowledge of scientific concepts",
            iconResId = android.R.drawable.ic_menu_compass
        ),
        Category(
            id = 2,
            name = "History",
            description = "Explore historical events and figures",
            iconResId = android.R.drawable.ic_menu_recent_history
        ),
        Category(
            id = 3,
            name = "Technology",
            description = "Questions about modern technology",
            iconResId = android.R.drawable.ic_menu_manage
        )
    )

    val questions = listOf(
        Question(
            id = 1,
            question = "What is the closest planet to the Sun?",
            options = listOf("Venus", "Mercury", "Mars", "Earth"),
            correctAnswer = 1,
            difficulty = Difficulty.EASY,
            categoryId = 1,
            explanation = "Mercury is the closest planet to the Sun in our solar system."
        ),
        Question(
            id = 2,
            question = "Who was the first President of the United States?",
            options = listOf(
                "Thomas Jefferson",
                "John Adams",
                "George Washington",
                "Benjamin Franklin"
            ),
            correctAnswer = 2,
            difficulty = Difficulty.EASY,
            categoryId = 2,
            explanation = "George Washington served as the first President of the United States from 1789 to 1797."
        ),
        Question(
            id = 3,
            question = "Which programming language is primarily used for Android development?",
            options = listOf("Swift", "Kotlin", "Python", "Ruby"),
            correctAnswer = 1,
            difficulty = Difficulty.MEDIUM,
            categoryId = 3,
            explanation = "Kotlin is the preferred programming language for Android development, as recommended by Google."
        ),
        Question(
            id = 4,
            question = "What is the chemical symbol for Gold?",
            options = listOf("Ag", "Au", "Fe", "Cu"),
            correctAnswer = 1,
            difficulty = Difficulty.EASY,
            categoryId = 1,
            explanation = "Au is the chemical symbol for Gold, derived from the Latin word 'Aurum'."
        ),
        Question(
            id = 5,
            question = "In which year did World War II end?",
            options = listOf("1943", "1944", "1945", "1946"),
            correctAnswer = 2,
            difficulty = Difficulty.MEDIUM,
            categoryId = 2,
            explanation = "World War II ended in 1945 with the surrender of Germany and Japan."
        )
    )

    fun getQuestionsByCategory(categoryId: Int): List<Question> {
        return questions.filter { it.categoryId == categoryId }
    }

    fun getQuestionsByDifficulty(difficulty: Difficulty): List<Question> {
        return questions.filter { it.difficulty == difficulty }
    }
} 