package com.example.database.models

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
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
    val wordId = integer("word_id").references(Words.id, onDelete = ReferenceOption.CASCADE) // Ссылка на таблицу Words
    val langId = integer("lang_id").references(Langs.id, onDelete = ReferenceOption.CASCADE) // Ссылка на таблицу Langs
    val partOfSpeech = varchar("part_of_speech", 25).nullable() // Часть речи
    val audioUrl = varchar("audio_url", 255).nullable() // URL аудио
    val definition = text("definition").nullable() // Определение
    //val createdAt = datetime("created_at").defaultExpression(CurrentDateTime) // Время создания
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp)

}

// Таблица для перевода слов
object Translations : IntIdTable("translations") {
    val wordDataId = integer("word_data_id").references(WordsData.id, onDelete = ReferenceOption.CASCADE) // Ссылка на таблицу WordsData
    val translation = text("translation") // Перевод слова
}

// Таблица для примеров предложений
object ExampleSentences : IntIdTable("example_sentences") {
    val wordDataId = integer("word_data_id").references(WordsData.id, onDelete = ReferenceOption.CASCADE) // Ссылка на таблицу WordsData
    val exampleSentence = text("example_sentence") // Пример предложения
}

// Таблица для переводов примеров предложений
object ExampleSentenceTranslations : IntIdTable("example_sentences_translations") {
    val exampleSentenceId = integer("example_sentence_id").references(ExampleSentences.id, onDelete = ReferenceOption.CASCADE) // Ссылка на таблицу ExampleSentences
    val langId = integer("lang_id").references(Langs.id, onDelete = ReferenceOption.CASCADE) // Ссылка на таблицу Langs
    val exampleSentenceTranslation = text("example_sentence_translation") // Перевод примера

}

// Таблица для выученных слов пользователя
object UserLearnedWords : IntIdTable("user_learned_words") {
    val userId = integer("user_id").references(UsersTable.id, onDelete = ReferenceOption.CASCADE)
    val langId = integer("lang_id").references(Langs.id, onDelete = ReferenceOption.CASCADE)
    val wordDataId = integer("word_data_id").references(WordsData.id, onDelete = ReferenceOption.CASCADE)
    val learnedDate = date("learned_date")

}
