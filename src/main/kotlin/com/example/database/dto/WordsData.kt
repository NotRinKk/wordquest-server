import kotlinx.serialization.Serializable


@Serializable
data class Phonetic(
    val text: String,
    val audio: String,
    val sourceUrl: String,
    val license: LicenseDetails
)

@Serializable
data class LicenseDetails(
    val name: String,
    val url: String
)

@Serializable
data class Meaning(
    val partOfSpeech: String,
    val definitions: List<Definition>,
    val synonyms: List<String>,
    val antonyms: List<String>
)

@Serializable
data class Definition(
    val definition: String,
    val synonyms: List<String> = emptyList(),
    val antonyms: List<String> = emptyList(),
    val example: String? = null
)

@Serializable
data class License(
    val name: String,
    val url: String
)


@Serializable
data class ApiWordMeaningItem(
    val word: String,
    val phonetic: String? = null,
    val phonetics: List<Phonetic>,
    val meanings: List<Meaning>,
    val license: License,
    val sourceUrls: List<String>
)
