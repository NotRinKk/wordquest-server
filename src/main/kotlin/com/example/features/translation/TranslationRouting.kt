package com.example.features.translation

import com.example.api.ApiService
import com.example.database.dao.words.PostgresWordDataRepository
import io.ktor.client.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Application.configureSerializationWordDataTranslation(repository: PostgresWordDataRepository) {
    routing {
        get("/api/word/data/translation") {
            val client = HttpClient {
                install(Logging) {
                    level = LogLevel.ALL
                    logger = Logger.DEFAULT
                }
            }

            try {
                val apiService = ApiService(client)
                val wordDataToTranslate = repository.getWordsDataForTranslation()
                for(dataToTranslatePerWord in wordDataToTranslate) {

                    val wordDefinition = apiService.getTranslation(dataToTranslatePerWord)

                    val definitionCount = dataToTranslatePerWord.definition.count()
                    val examplesCount = dataToTranslatePerWord.exampleSentences?.count()
                    // Сохраняем переводы в базу
                    repository.saveTranslations(dataToTranslatePerWord.wordId, wordDefinition, definitionCount, examplesCount ?: 0)
                }

                call.respond(HttpStatusCode.Created)
            } catch (ex: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, "ERROR")
            }
        }
    }
}