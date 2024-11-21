package com.example.features.login

import com.example.database.dao.user.PostgresUserRepository
import com.example.database.dto.UserLogin
import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.serialization.*
//import io.ktor.serialization.kotlinx.json.*
//import io.ktor.server.plugins.contentnegotiation.*

fun Application.configureSerializationUserLogin(repository: PostgresUserRepository) {
    routing {
        post("/api/users/login") {
            try {
                val userRecieve = call.receive<UserLogin>()
                val user = repository.fetchUserByUsername(userRecieve.username)
                if (user == null) {
                    call.respond(HttpStatusCode.BadRequest, "User not found")
                } else {
                    if (user.password_hash == userRecieve.password_hash) {
                        call.respond(HttpStatusCode.OK)
                    }
                    else {
                        call.respond(HttpStatusCode.BadRequest, "Invalid password")
                    }
                }
            } catch (ex: IllegalStateException) {
                call.respond(HttpStatusCode.BadRequest)
            } catch (ex: JsonConvertException) {
                call.respond(HttpStatusCode.BadRequest)
            } catch (e: Exception) {
                call.application.environment.log.error("Error processing login", e)
                call.respond(HttpStatusCode.InternalServerError)
            }

        }
    }
}