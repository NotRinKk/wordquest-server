package com.example.database.dto

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val username: String,
    val password_hash: String,
    val email: String
)