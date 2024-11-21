package com.example.database.dao.words

import ApiWordMeaningItem

interface WordDataRepository {
    suspend fun insertWordData(wordDefinition: ApiWordMeaningItem)
}