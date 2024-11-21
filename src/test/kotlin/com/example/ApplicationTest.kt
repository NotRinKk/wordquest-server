package com.example

import com.example.database.dao.user.PostgresUserRepository
import io.ktor.server.testing.*
import kotlin.test.*

class ApplicationTest {
    @Test
    fun newTasksCanBeAdded() = testApplication {
        application {
            val repository =  PostgresUserRepository()
            configureRouting()
        }

//// Данные для регистрации
//        val registerRequest = RegisterReceiveRemote(
//            login = "testUser",
//            email = "test@example.com",
//            password = "password123"
//        )
//
//        // Выполняем POST запрос на регистрацию
//        val response = client.post("/api/users/register") {
//            contentType(ContentType.Application.Json)
//            setBody(Json.encodeToString(registerRequest))
//        }
//
//        // Проверяем, что статус ответа 201 Created
//        assertEquals(HttpStatusCode.Created, response.status)
//        assertEquals("User successfully registered", response.bodyAsText())
//    }
//    }
    }
}
