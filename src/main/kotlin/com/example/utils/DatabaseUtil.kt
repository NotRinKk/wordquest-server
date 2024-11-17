package com.example.utils

import org.jetbrains.exposed.sql.Database
import io.ktor.server.application.*
import io.github.cdimascio.dotenv.dotenv

fun Application.configureDatabases() {
    val dotenv = dotenv()
    Database.connect(
        url = dotenv["DB_URL"],
        driver = dotenv["DB_DRIVER"],
        user = dotenv["DB_USER"],
        password = dotenv["DB_USER_PASS"]
    )

}