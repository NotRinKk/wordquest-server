package com.example.features.new_word

import com.example.api.ApiService
import com.example.database.dao.words.PostgresWordDataRepository
import com.example.database.dto.NewWordRequest
import io.ktor.client.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Application.configureSerializationNewWord(repository: PostgresWordDataRepository) {
    routing {
        post("/api/word/data/new") {
            try {
                // Получаем список исключённых ID
                val newWordRequest = call.receive<NewWordRequest>()
                val excludedIds = newWordRequest.ids

                // Получаем новое слово
                val wordResponse = repository.getNewRandomWord(excludedIds)

                call.respond(HttpStatusCode.OK, wordResponse)
            } catch (ex: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, "ERROR")
            }
        }
    }
}