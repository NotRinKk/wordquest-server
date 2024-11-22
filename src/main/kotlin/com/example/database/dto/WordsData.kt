import kotlinx.serialization.Serializable


@Serializable
data class Phonetic(
    val text: String? = null,
    val audio: String,
    val sourceUrl: String? = null,
    val license: LicenseDetails? = null
)

@Serializable
data class LicenseDetails(
    val name: String? = null,
    val url: String? = null
)

@Serializable
data class Meaning(
    val partOfSpeech: String,
    val definitions: List<Definition>,
    val synonyms: List<String> = emptyList(),
    val antonyms: List<String> = emptyList()
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
    val name: String? = null,
    val url: String? = null
)


@Serializable
data class ApiWordMeaningItem(
    val word: String,
    val phonetic: String? = null,
    val phonetics: List<Phonetic>,
    val meanings: List<Meaning>,
    val license: License? = null ,
    val sourceUrls: List<String> ? = null
)
