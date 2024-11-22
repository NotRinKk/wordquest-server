package com.example.database.models

data class WordTranslationData(
    val wordId: Int,
    val wordText: String,
    val definition: List<String>,
    val exampleSentences: List<String>?
)
