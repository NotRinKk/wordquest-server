package com.example.features.register

import com.example.database.dao.PostgresUserRepository
import com.example.database.dto.User
import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.serialization.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*

fun Application.configureSerializationUser(repository: PostgresUserRepository) {
    install(ContentNegotiation) {
        json()
    }
        routing {
        post("/api/users/register") {
            try {
                val username = call.receive<User>()
                call.application.environment.log.info("$username ADD")
                repository.addUser(username)
                call.respond(HttpStatusCode.Created)
            } catch (ex: IllegalStateException) {
                call.respond(HttpStatusCode.BadRequest)
            } catch (ex: JsonConvertException) {
                call.respond(HttpStatusCode.BadRequest)
            } catch (e: Exception) {
                call.application.environment.log.error("Error processing registration", e)
                call.respond(HttpStatusCode.InternalServerError)
            }

        }
    }
}