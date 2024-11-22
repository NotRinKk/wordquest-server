package com.example.features.register

import com.example.database.dao.user.PostgresUserRepository
import com.example.database.dto.User
import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.serialization.*

fun Application.configureSerializationUserRegister(repository: PostgresUserRepository) {

        routing {
        post("/api/users/register") {
            try {
                val user = call.receive<User>()
                val userRegistered = repository.fetchUserByUsername(user.username)
                if (userRegistered == null) {
                    repository.addUser(user)
                    call.respond(HttpStatusCode.Created, "User registered successfully")
                } else {
                    call.respond(HttpStatusCode.Conflict,  "User with this username already exists")
                }
            } catch (ex: IllegalStateException) {
                call.respond(HttpStatusCode.BadRequest, "Error: ${ex.message}")
            } catch (ex: JsonConvertException) {
                call.respond(HttpStatusCode.BadRequest)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError)
            }
        }
    }
}