package com.example.database.dao.words

import ApiWordMeaningItem
import com.example.database.dto.TranslationResponse
import com.example.database.models.*
import com.example.database.suspendTransaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class PostgresWordDataRepository {
    suspend fun insertWordData(wordDefinition: ApiWordMeaningItem) = suspendTransaction {
        val langId = Langs
            .select(Langs.id)
            .where(Langs.languageCode eq "en")
            .map { it[Langs.id] }
            .first()
            .value

        val wordId = Words
            .select(Words.id)
            .where(Words.wordText eq wordDefinition.word)
            .map {
                it[Words.id]
            }
            .first()
            .value

        //var wordDataIdList = listOf<Int>()
        wordDefinition.meanings.forEach { meaning ->
            val wordDataId = WordsData
                .insert {
                    it[WordsData.wordId] = wordId
                    it[WordsData.langId] = langId
                    it[partOfSpeech] = meaning.partOfSpeech
                    it[audioUrl] = wordDefinition.phonetics.firstOrNull()?.audio
                    it[definition] = meaning.definitions.first().definition
                }.resultedValues?.firstOrNull()?.get(WordsData.wordId)

            if (wordDataId != null) {
                // Вставляем примеры предложений
                meaning.definitions.forEach { definition ->
                    definition.example?.let { exampleSentence ->
                        ExampleSentences.insertIgnore {
                            it[ExampleSentences.wordDataId] = wordDataId
                            it[ExampleSentences.exampleSentence] = exampleSentence
                        }
                    }
                }
            }
        }
    }

    // Получение всех данных для перевода
    suspend fun getWordsDataForTranslation(): List<WordTranslationData>  = suspendTransaction {
        val wordDataList = mutableListOf<WordTranslationData>()
        transaction {
            // Получаем все записи из WordsData
            val wordsData = WordsData.selectAll().toList()

            for(wordData in wordsData) {
                // Получаем слово из таблицы Words по wordId
                val wordText = Words
                    .select(Words.wordText)
                    .where(Words.id eq wordData[WordsData.wordId])
                    .single()
                    .get(Words.wordText)
                // Получаем все определения для слова
                val definitions = WordsData
                    .select(WordsData.definition)
                    .where(WordsData.wordId eq wordData[WordsData.wordId])
                    .map { it[WordsData.definition]}

                // Получаем примеры предложений по wordDataId
                val exampleSentences = ExampleSentences
                    .select(ExampleSentences.exampleSentence)
                    .where(ExampleSentences.wordDataId eq wordData[WordsData.wordId])
                    .map { it[ExampleSentences.exampleSentence] }
                // Получаем значения из Definition

                wordDataList.add(WordTranslationData(wordData[WordsData.wordId],wordText, definitions, exampleSentences))
            }

        }
        wordDataList
    }

    suspend fun saveTranslations(wordDataId: Int, translations: TranslationResponse, definitionCount: Int, exampleCount: Int) = suspendTransaction {

        // 1. Сохраняем перевод самого слова в таблицу Translations
        if (translations.translations.isNotEmpty()) {
            val wordTranslation = translations.translations.firstOrNull()
            wordTranslation?.let {
                Translations.insert {
                    it[Translations.wordDataId] = wordDataId
                    it[Translations.translation] = wordTranslation.text
                }
            }

            // 2. Сохраняем все переводы определения в таблицу DefinitionTranslations
            for(i in 1..definitionCount) {
                DefinitionTranslations.insert{
                    it[DefinitionTranslations.wordDataId] = wordDataId
                    it[DefinitionTranslations.definition] = translations.translations[i].text
                }
            }
            // 3. Получаем примеры предложений для текущего слова (wordDataId)

            val exampleSentencesIds = ExampleSentences
                .select (ExampleSentences.id)
                .where(ExampleSentences.wordDataId eq wordDataId)
                .map { it[ExampleSentences.id].value}


            val exampleSentencesIdCount = exampleSentencesIds.count()

            if(exampleSentencesIdCount != 0) {
                var exampleId = 0

                // 4. Сохраняем переводы примеров предложений в таблицу ExampleSentenceTranslations
                for (i in (definitionCount + 1)..(definitionCount + exampleCount)) {
                    ExampleSentenceTranslations.insert {
                        it[ExampleSentenceTranslations.exampleSentenceId] = exampleSentencesIds[exampleId]
                        it[ExampleSentenceTranslations.langId] = 1
                        it[ExampleSentenceTranslations.exampleSentenceTranslation] = translations.translations[i].text
                    }
                    ++exampleId
                }
            }
        }
    }
}