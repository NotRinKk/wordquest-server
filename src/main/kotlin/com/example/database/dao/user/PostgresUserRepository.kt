package com.example.database.dao.user

import com.example.database.dto.User
import com.example.database.models.UsersTable
import com.example.database.suspendTransaction
import org.jetbrains.exposed.sql.*


class PostgresUserRepository : UsersRepository {
    override suspend fun fetchUserByUsername(username: String): User? = suspendTransaction {
        val userModel = UsersTable.selectAll().where { UsersTable.username eq username }.singleOrNull()
        if (userModel == null) {
            null
        } else {
            User(
                username = userModel[UsersTable.username],
                password_hash = userModel[UsersTable.password_hash],
                email = userModel[UsersTable.email]
            )
        }
}

    override suspend fun addUser(user: User) : Unit = suspendTransaction {
        try {
            UsersTable.insert {
                it[username] = user.username
                it[password_hash] = user.password_hash
                it[email] = user.email
            }
        } catch (e: Exception) {
            println("ERROR: $e")
        }

    }
}