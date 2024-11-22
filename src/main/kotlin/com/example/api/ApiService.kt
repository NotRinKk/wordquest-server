package com.example.api

import ApiWordMeaningItem
import com.example.database.dto.TranslateRequest
import com.example.database.dto.TranslationResponse
import com.example.database.models.WordTranslationData
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import io.github.cdimascio.dotenv.dotenv
import kotlinx.serialization.encodeToString

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

    suspend fun getTranslation(wordTranslationData: WordTranslationData) : TranslationResponse {
        val dotenv = dotenv()
        val BASE_URL = dotenv["API_URL_TRANSLATION"]
        val token = dotenv["TOKEN"]

        val textsToTranslate = wordTranslationData.exampleSentences?.let {
            listOf(
                wordTranslationData.wordText,
                *wordTranslationData.definition.toTypedArray(),
                *it.toTypedArray()
            )
        }

        val request = textsToTranslate?.let {
            TranslateRequest(
                sourceLanguageCode = "en",
                targetLanguageCode = "ru",
                format = "PLAIN_TEXT",
                texts = it,
                folderId = dotenv["FOLDER_ID"],
                speller = false
            )
        }
        val response: HttpResponse = client.post {
            url(BASE_URL)
            header(HttpHeaders.ContentType,ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer $token")
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(request))
        }

        val bd = Json.encodeToString(request)

        if (response.status != HttpStatusCode.OK) {
            throw IllegalArgumentException("Error fetching word data translation")
        }
        val responseBody = response.bodyAsText()
        return Json.decodeFromString<TranslationResponse>(responseBody)
    }
}
