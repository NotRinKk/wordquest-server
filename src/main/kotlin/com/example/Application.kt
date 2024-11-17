package com.example

import com.example.database.dao.PostgresUserRepository
//import com.example.features.register.configureRegisterRouting
import com.example.features.register.configureSerializationUser
import com.example.utils.configureDatabases
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)


}

fun Application.module() {
    val repository = PostgresUserRepository()
    configureDatabases()
    configureSerializationUser(repository)
    configureMonitoring()
    configureRouting()
}
