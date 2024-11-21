package com.example.api

import ApiWordMeaningItem
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import io.github.cdimascio.dotenv.dotenv

class ApiService(private val client: HttpClient) {

    suspend fun getWordMeaning(word: String): List<ApiWordMeaningItem> {
        val dotenv = dotenv()
        val BASE_URL = dotenv["API_URL_WORD_DEFINITION"]
        val response: HttpResponse = client.get("$BASE_URL$word")
        if (response.status != HttpStatusCode.OK) {
            throw IllegalArgumentException("Error fetching word meaning")
        }
        val responseBody = response.bodyAsText()
        return  Json.decodeFromString<List<ApiWordMeaningItem>>(responseBody)
    }
}
