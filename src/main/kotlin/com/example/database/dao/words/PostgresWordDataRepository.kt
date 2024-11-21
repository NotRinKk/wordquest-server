package com.example.database.dao.words

import ApiWordMeaningItem
import com.example.database.models.ExampleSentences
import com.example.database.models.Langs
import com.example.database.models.Words
import com.example.database.models.WordsData
import com.example.database.suspendTransaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insertIgnore

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

        wordDefinition.meanings.forEach { meaning ->
            val wordDataId = WordsData
                .insertIgnore {
                    it[WordsData.wordId] = wordId
                    it[WordsData.langId] = langId
                    it[partOfSpeech] = meaning.partOfSpeech
                    it[audioUrl] = wordDefinition.phonetics.firstOrNull()?.audio
                    it[definition] = meaning.definitions.firstOrNull()?.definition
                }.resultedValues?.firstOrNull()?.get(WordsData.id)?.value

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

}