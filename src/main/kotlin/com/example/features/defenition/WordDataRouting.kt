package com.example.features.defenition

import com.example.api.ApiService
import com.example.database.dao.words.PostgresWordDataRepository
import io.ktor.client.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Application.configureSerializationWordDefinition(repository: PostgresWordDataRepository) {
    routing {
        get("/api/word/data/{word}") {
            val client = HttpClient()

            val word = call.parameters["word"]
            if (word == null) {
                call.respond(HttpStatusCode.BadRequest, "No word specified")
                return@get
            }
            try {
                val apiService = ApiService(client)
                val wordDefinition = apiService.getWordMeaning(word)
                wordDefinition.forEach{ defenition ->
                    repository.insertWordData(defenition)
                }
                //call.respond(wordDefinition)
                call.respond(HttpStatusCode.Created)
            } catch (ex: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, "ERROR")
            }
        }
    }
}