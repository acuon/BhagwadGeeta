package com.example.bhagwadgeeta.ui.home.model


data class VerseItem(
    val chapter_number: Int,
    val commentaries: List<Commentary>,
    val id: Int,
    val slug: String,
    val text: String,
    val translations: List<Translation>,
    val transliteration: String,
    val verse_number: Int,
    val word_meanings: String
)

data class Commentary(
    val author_name: String,
    val description: String,
    val id: Int,
    val language: String
)

data class Translation(
    val author_name: String,
    val description: String,
    val id: Int,
    val language: String
)