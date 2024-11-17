package com.example

import com.example.database.dao.PostgresUserRepository
import com.example.features.login.configureSerializationUserLogin
//import com.example.features.register.configureRegisterRouting
import com.example.features.register.configureSerializationUserRegister
import com.example.utils.configureDatabases
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)


}

fun Application.module() {
    val repository = PostgresUserRepository()
    configureDatabases()
    configureSerialization()
    configureSerializationUserRegister(repository)
    configureSerializationUserLogin(repository)
    configureMonitoring()
    configureRouting()
}
