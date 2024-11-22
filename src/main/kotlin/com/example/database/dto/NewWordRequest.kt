package com.example.database.dto

import kotlinx.serialization.Serializable

@Serializable
data class NewWordRequest(
    val ids : List<Int>
)
