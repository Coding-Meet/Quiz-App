package com.coding.meet.quizapp.data.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TriviaResponse(
    @SerialName("response_code")
    val responseCode: Int,
    @SerialName("results")
    val results: List<TriviaQuestion>
)

@Serializable
data class TriviaQuestion(
    @SerialName("category")
    val category: String,
    @SerialName("type")
    val type: String,
    @SerialName("difficulty")
    val difficulty: String,
    @SerialName("question")
    val question: String,
    @SerialName("correct_answer")
    val correctAnswer: String,
    @SerialName("incorrect_answers")
    val incorrectAnswers: List<String>
) 