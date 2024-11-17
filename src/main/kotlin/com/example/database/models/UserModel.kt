package com.example.database.models

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.Table


object UsersTable : Table("users") {
    val id = integer("user_id").autoIncrement()
    val username = varchar("username", 50)
    val password_hash = varchar("password_hash", 255)
    val email = varchar("email", 100)
}
