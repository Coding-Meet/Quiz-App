package com.coding.meet.quizapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coding.meet.quizapp.model.Question
import com.coding.meet.quizapp.model.QuizState
import com.coding.meet.quizapp.data.repository.QuizRepository
import com.coding.meet.quizapp.data.api.TriviaApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {
    private val _quizState = MutableStateFlow(QuizState())
    val quizState: StateFlow<QuizState> = _quizState.asStateFlow()
    private val repository = QuizRepository()

    init {
        Log.d("QuizViewModel", "ViewModel initialized")
    }

    fun loadQuestions(
        category: Int? = null,
        difficulty: String? = null,
        amount: Int = 10
    ) {
        viewModelScope.launch {
            try {
                repository.getQuestions(amount, category, difficulty)
                    .onSuccess { questions ->
                        startQuiz(questions)
                    }
                    .onFailure { error ->
                        Log.e("QuizViewModel", "Error loading questions", error)
                        // Handle error state if needed
                    }
            } catch (e: Exception) {
                Log.e("QuizViewModel", "Error loading questions", e)
                // Handle error state if needed
            }
        }
    }

    fun startQuiz(questions: List<Question>) {
        Log.d("QuizViewModel", "Starting quiz with ${questions.size} questions")
        if (questions.isEmpty()) {
            Log.e("QuizViewModel", "No questions provided")
            return
        }

        viewModelScope.launch {
            _quizState.update { currentState ->
                currentState.copy(
                    questions = questions,
                    currentQuestionIndex = 0,
                    score = 0,
                    selectedAnswer = null,
                    isQuizComplete = false
                )
            }
            Log.d("QuizViewModel", "Quiz state updated with first question: ${getCurrentQuestion()?.question}")
        }
    }

    fun selectAnswer(answerIndex: Int) {
        Log.d("QuizViewModel", "Answer selected: $answerIndex")
        _quizState.update { currentState ->
            currentState.copy(selectedAnswer = answerIndex)
        }
    }

    fun moveToNextQuestion() {
        val currentState = _quizState.value
        val currentQuestion = getCurrentQuestion()
        
        if (currentQuestion == null) {
            Log.e("QuizViewModel", "Current question is null")
            return
        }

        val isCorrect = currentState.selectedAnswer == currentQuestion.correctAnswer
        val newScore = if (isCorrect) currentState.score + 1 else currentState.score
        val nextIndex = currentState.currentQuestionIndex + 1
        val isComplete = nextIndex >= currentState.questions.size

        Log.d("QuizViewModel", "Moving to next question. Current score: $newScore, Next index: $nextIndex, Is complete: $isComplete")

        _quizState.update { state ->
            state.copy(
                currentQuestionIndex = nextIndex,
                score = newScore,
                selectedAnswer = null,
                isQuizComplete = isComplete
            )
        }
    }

    fun getCurrentQuestion(): Question? {
        val question = quizState.value.questions.getOrNull(quizState.value.currentQuestionIndex)
        Log.d("QuizViewModel", "Getting current question: ${question?.question}")
        return question
    }

    fun restartQuiz() {
        Log.d("QuizViewModel", "Restarting quiz")
        loadQuestions() // Load new questions instead of reusing old ones
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