package com.example.database.models

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.*

// Таблица для языков
object Langs : IntIdTable("lang") {
    val languageName = varchar("language_name", 50).uniqueIndex() // Название языка
    val languageCode = varchar("language_code", 3).uniqueIndex() // Код языка (например, en)

}

// Таблица для слов
object Words : IntIdTable("words") {
    val wordText = varchar("word_text", 100).uniqueIndex() // Слово
}

// Таблица для данных слов по языкам
object WordsData : IntIdTable("words_data") {
    val wordId = integer("word_id")
    val langId = integer("lang_id")
    val partOfSpeech = varchar("part_of_speech", 25).nullable() // Часть речи
    val audioUrl = varchar("audio_url", 255).nullable() // URL аудио
    val definition = text("definition") // Определение
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp)

}

// Таблица для перевода слов
object Translations : IntIdTable("translations") {
    val wordDataId = integer("word_id")
    val translation = text("translation").uniqueIndex() // Перевод слова
}

// Таблица для перевода определений слов
object DefinitionTranslations : IntIdTable("defenition_translations") {
    val wordDataId = integer("word_id")
    val definition = text("defenition").uniqueIndex() // Перевод слова
}

// Таблица для примеров предложений
object ExampleSentences : IntIdTable("example_sentences") {
    val wordDataId = integer("word_id")
    val exampleSentence = text("example_sentence").uniqueIndex() // Пример предложения
}

// Таблица для переводов примеров предложений
object ExampleSentenceTranslations : IntIdTable("example_sentences_translations") {
    val exampleSentenceId = integer("example_sentence_id")
    val langId = integer("lang_id")
    val exampleSentenceTranslation = text("example_sentence_translation").uniqueIndex() // Перевод примера

}

// Таблица для выученных слов пользователя
object UserLearnedWords : IntIdTable("user_learned_words") {
    val userId = integer("user_id")
    val langId = integer("lang_id")
    val wordDataId = integer("word_data_id")
    val learnedDate = date("learned_date")

}
