package com.coding.meet.quizapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coding.meet.quizapp.data.repository.QuizRepository
import com.coding.meet.quizapp.data.api.TriviaApiClient
import com.coding.meet.quizapp.mvi.QuizContract
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class QuizViewModel(
    private val repository: QuizRepository = QuizRepository()
) : ViewModel() {
    private val _state = MutableStateFlow(QuizContract.State())
    val state: StateFlow<QuizContract.State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<QuizContract.Effect>()
    val effect: SharedFlow<QuizContract.Effect> = _effect.asSharedFlow()

    init {
        Log.d("QuizViewModel", "ViewModel initialized")
    }

    fun onEvent(event: QuizContract.Event) {
        when (event) {
            is QuizContract.Event.LoadQuestions -> loadQuestions(event.categoryId)
            is QuizContract.Event.SelectAnswer -> selectAnswer(event.answerIndex)
            is QuizContract.Event.NextQuestion -> moveToNextQuestion()
            is QuizContract.Event.RestartQuiz -> restartQuiz()
            is QuizContract.Event.SelectCategory -> selectCategory(event.categoryId)
        }
    }

    private fun loadQuestions(categoryId: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                repository.getQuestions(category = categoryId)
                    .onSuccess { questions ->
                        _state.update { 
                            it.copy(
                                questions = questions,
                                isLoading = false,
                                currentQuestionIndex = 0,
                                score = 0,
                                selectedAnswer = null,
                                isQuizComplete = false
                            )
                        }
                    }
                    .onFailure { error ->
                        _state.update { it.copy(isLoading = false) }
                        _effect.emit(QuizContract.Effect.ShowError(error.message ?: "Failed to load questions"))
                    }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false) }
                _effect.emit(QuizContract.Effect.ShowError(e.message ?: "An unexpected error occurred"))
            }
        }
    }

    private fun selectAnswer(answerIndex: Int) {
        _state.update { it.copy(selectedAnswer = answerIndex) }
    }

    private fun moveToNextQuestion() {
        val currentState = _state.value
        val currentQuestion = currentState.currentQuestion
        
        if (currentQuestion == null) {
            viewModelScope.launch {
                _effect.emit(QuizContract.Effect.ShowError("No question available"))
            }
            return
        }

        val isCorrect = currentState.selectedAnswer == currentQuestion.correctAnswer
        val newScore = if (isCorrect) currentState.score + 1 else currentState.score
        val nextIndex = currentState.currentQuestionIndex + 1
        val isComplete = nextIndex >= currentState.questions.size

        _state.update { 
            it.copy(
                currentQuestionIndex = nextIndex,
                score = newScore,
                selectedAnswer = null,
                isQuizComplete = isComplete
            )
        }

        if (isCorrect) {
            viewModelScope.launch {
                _effect.emit(QuizContract.Effect.ShowToast("Correct answer!"))
            }
        }
    }

    private fun restartQuiz() {
        val categoryId = _state.value.selectedCategoryId
        if (categoryId != null) {
            loadQuestions(categoryId)
        }
    }

    private fun selectCategory(categoryId: Int) {
        _state.update { it.copy(selectedCategoryId = categoryId) }
        loadQuestions(categoryId)
    }

    companion object {
        val CATEGORIES = mapOf(
            "General Knowledge" to TriviaApiClient.CATEGORY_GENERAL_KNOWLEDGE,
            "Science" to TriviaApiClient.CATEGORY_SCIENCE,
            "Computers" to TriviaApiClient.CATEGORY_COMPUTERS,
            "Mathematics" to TriviaApiClient.CATEGORY_MATHEMATICS,
            "Mythology" to TriviaApiClient.CATEGORY_MYTHOLOGY,
            "Sports" to TriviaApiClient.CATEGORY_SPORTS,
            "Geography" to TriviaApiClient.CATEGORY_GEOGRAPHY,
            "History" to TriviaApiClient.CATEGORY_HISTORY,
            "Politics" to TriviaApiClient.CATEGORY_POLITICS,
            "Art" to TriviaApiClient.CATEGORY_ART
        )

        val DIFFICULTIES = listOf(
            TriviaApiClient.DIFFICULTY_EASY,
            TriviaApiClient.DIFFICULTY_MEDIUM,
            TriviaApiClient.DIFFICULTY_HARD
        )
    }
} 