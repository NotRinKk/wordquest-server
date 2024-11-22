package com.example.database.dao.words

import ApiWordMeaningItem
import com.example.database.dto.TranslationResponse
import com.example.database.models.WordTranslationData

interface WordDataRepository {
    suspend fun insertWordData(wordDefinition: ApiWordMeaningItem)
    suspend fun getWordsDataForTranslation(): List<WordTranslationData>
    suspend fun saveTranslations(wordDataId: Int, translations: TranslationResponse, definitionCount: Int, exampleCount: Int)
}