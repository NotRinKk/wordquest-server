package com.example.database.dto

import kotlinx.serialization.Serializable

@Serializable
data class TranslateRequest(
    val sourceLanguageCode: String,
    val targetLanguageCode: String,
    val format: String,
    val texts: List<String>,
    val folderId: String,
    val speller: Boolean
)