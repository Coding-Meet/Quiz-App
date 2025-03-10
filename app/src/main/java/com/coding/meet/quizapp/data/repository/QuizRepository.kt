package com.coding.meet.quizapp.data.repository

import com.coding.meet.quizapp.data.api.TriviaApiClient
import com.coding.meet.quizapp.data.api.TriviaQuestion
import com.coding.meet.quizapp.model.Question
import com.coding.meet.quizapp.model.Difficulty

class QuizRepository(
    private val apiClient: TriviaApiClient = TriviaApiClient()
) {
    suspend fun getQuestions(
        amount: Int = 10,
        category: Int? = null,
        difficulty: String? = null
    ): Result<List<Question>> {
        return apiClient.getQuestions(amount, category, difficulty)
            .map { response ->
                response.results.mapIndexed { index, triviaQuestion ->
                    convertToQuestion(triviaQuestion, index)
                }
            }
    }

    private fun convertToQuestion(triviaQuestion: TriviaQuestion, id: Int): Question {
        val allOptions = (triviaQuestion.incorrectAnswers + triviaQuestion.correctAnswer).shuffled()
        val correctAnswerIndex = allOptions.indexOf(triviaQuestion.correctAnswer)

        return Question(
            id = id,
            question = triviaQuestion.question,
            options = allOptions,
            correctAnswer = correctAnswerIndex,
            difficulty = when (triviaQuestion.difficulty.toLowerCase()) {
                "easy" -> Difficulty.EASY
                "medium" -> Difficulty.MEDIUM
                "hard" -> Difficulty.HARD
                else -> Difficulty.MEDIUM
            },
            categoryId = 0, // We'll map this later if needed
            explanation = "Correct answer: ${triviaQuestion.correctAnswer}"
        )
    }
} 