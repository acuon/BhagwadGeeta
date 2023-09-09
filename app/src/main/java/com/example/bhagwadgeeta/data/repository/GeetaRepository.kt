package com.example.bhagwadgeeta.data.repository

import com.example.bhagwadgeeta.data.database.GeetaDao
import com.example.bhagwadgeeta.data.database.Temp
import com.example.bhagwadgeeta.data.remote.GeetaService
import com.example.bhagwadgeeta.ui.home.model.ChapterItem
import com.example.bhagwadgeeta.ui.home.model.VerseItem
import javax.inject.Inject

class GeetaRepository @Inject constructor(
    private val geetaService: GeetaService,
    private val geetaDao: GeetaDao
) {
    suspend fun getAllChapters(limit: Int): List<ChapterItem?>? {
        return geetaService.getAllChapters(limit)
    }

    suspend fun getParticularChapter(chapterNumber: Int): ChapterItem? {
        return geetaService.getParticularChapter(chapterNumber)
    }

    suspend fun getAllVerses(chapterNumber: Int): List<VerseItem?>? {
        return geetaService.getAllVersesFromChapter(chapterNumber)
    }

    suspend fun getParticularVerse(chapterNumber: Int, verseNumber: Int): VerseItem? {
        return geetaService.getParticularVerse(chapterNumber, verseNumber)
    }

}