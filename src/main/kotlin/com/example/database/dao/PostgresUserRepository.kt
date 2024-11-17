package com.example.database.dao

//import com.example.database.dto.UserDAO
import com.example.database.dto.User
import com.example.database.models.UsersTable
//import com.example.database.dto.daoToModel
import com.example.database.models.suspendTransaction
import org.jetbrains.exposed.sql.insert

class PostgresUserRepository : UsersRepository {
    override suspend fun selectByUsername(username: String): User? = suspendTransaction {
        User("name", "pass", "mail")
}

    override suspend fun addUser(user: User) : Unit = suspendTransaction {
        try {
            println("START")
            UsersTable.insert {
                it[username] = user.username
                it[password_hash] = user.password_hash
                it[email] = user.email
            }
            println("User added successfully: $user")
        } catch (e: Exception) {
            println("ERROR: $e")
        }

    }
}