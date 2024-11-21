package com.example

import com.example.database.dao.user.PostgresUserRepository
import com.example.database.dao.words.PostgresWordDataRepository
import com.example.features.defenition.configureSerializationWordDefinition
import com.example.features.login.configureSerializationUserLogin
//import com.example.features.register.configureRegisterRouting
import com.example.features.register.configureSerializationUserRegister
import com.example.utils.configureDatabases
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)


}

fun Application.module() {
    val userRepository = PostgresUserRepository()
    val wordRepository = PostgresWordDataRepository()
    configureDatabases()
    configureSerialization()
    configureSerializationUserRegister(userRepository)
    configureSerializationUserLogin(userRepository)
    configureSerializationWordDefinition(wordRepository)
    configureMonitoring()
    configureRouting()
}
