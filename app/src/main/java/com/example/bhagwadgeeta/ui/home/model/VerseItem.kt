package com.example.bhagwadgeeta.ui.home.model


data class VerseItem(
    val chapter_number: Int? = null,
    val commentaries: List<Commentary?>? = null,
    val id: Int? = null,
    val slug: String? = null,
    val text: String? = null,
    val translations: List<Translation?>? = null,
    val transliteration: String? = null,
    val verse_number: Int? = null,
    val word_meanings: String? = null
)

data class Commentary(
    val author_name: String? = null,
    val description: String? = null,
    val id: Int? = null,
    val language: String? = null
)

data class Translation(
    val author_name: String? = null,
    val description: String? = null,
    val id: Int? = null,
    val language: String? = null
)