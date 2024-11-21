package com.example.database.dao.user

import com.example.database.dto.User

interface UsersRepository {
    suspend fun fetchUserByUsername(username: String): User?
    suspend fun addUser(user: User) : Unit
}