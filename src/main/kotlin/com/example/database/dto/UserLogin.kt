package com.example.database.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserLogin(
    val username: String,
    val password_hash: String
)
