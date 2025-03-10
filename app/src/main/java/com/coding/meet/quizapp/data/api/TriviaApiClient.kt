package com.coding.meet.quizapp.data.api

import android.util.Log
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class TriviaApiClient {
    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        isLenient = true
        encodeDefaults = true
    }

    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(json)
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d("TriviaAPI", message)
                }
            }
            level = LogLevel.ALL
        }
    }

    suspend fun getQuestions(
        amount: Int = 10,
        category: Int? = null,
        difficulty: String? = null,
        type: String = "multiple"
    ): Result<TriviaResponse> {
        return try {
            val response: TriviaResponse = client.get("https://opentdb.com/api.php") {
                url {
                    parameters.append("amount", amount.toString())
                    parameters.append("type", type)
                    category?.let { parameters.append("category", it.toString()) }
                    difficulty?.let { parameters.append("difficulty", it.toLowerCase()) }
                }
            }.body()
            
            Result.success(response)
        } catch (e: Exception) {
            Log.e("TriviaAPI", "Error fetching questions", e)
            Result.failure(e)
        }
    }

    companion object {
        const val CATEGORY_GENERAL_KNOWLEDGE = 9
        const val CATEGORY_SCIENCE = 17
        const val CATEGORY_COMPUTERS = 18
        const val CATEGORY_MATHEMATICS = 19
        const val CATEGORY_MYTHOLOGY = 20
        const val CATEGORY_SPORTS = 21
        const val CATEGORY_GEOGRAPHY = 22
        const val CATEGORY_HISTORY = 23
        const val CATEGORY_POLITICS = 24
        const val CATEGORY_ART = 25
        
        const val DIFFICULTY_EASY = "easy"
        const val DIFFICULTY_MEDIUM = "medium"
        const val DIFFICULTY_HARD = "hard"
    }
} 