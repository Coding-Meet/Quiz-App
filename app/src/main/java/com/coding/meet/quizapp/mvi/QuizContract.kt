package com.coding.meet.quizapp.mvi

import com.coding.meet.quizapp.model.Question

object QuizContract {
    sealed interface Event {
        data class LoadQuestions(val categoryId: Int) : Event
        data class SelectAnswer(val answerIndex: Int) : Event
        object NextQuestion : Event
        object RestartQuiz : Event
        data class SelectCategory(val categoryId: Int) : Event
    }

    data class State(
        val questions: List<Question> = emptyList(),
        val currentQuestionIndex: Int = 0,
        val selectedAnswer: Int? = null,
        val score: Int = 0,
        val isLoading: Boolean = false,
        val error: String? = null,
        val isQuizComplete: Boolean = false,
        val selectedCategoryId: Int? = null
    ) {
        val currentQuestion: Question?
            get() = questions.getOrNull(currentQuestionIndex)
        
        val progress: Float
            get() = if (questions.isEmpty()) 0f else (currentQuestionIndex + 1).toFloat() / questions.size
    }

    sealed interface Effect {
        object NavigateBack : Effect
        data class ShowError(val message: String) : Effect
        data class ShowToast(val message: String) : Effect
    }
} 