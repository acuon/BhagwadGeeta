package com.example.bhagwadgeeta.data.repository

import com.example.bhagwadgeeta.data.database.GeetaDao
import com.example.bhagwadgeeta.data.remote.GeetaService
import com.example.bhagwadgeeta.ui.home.model.ChapterItem
import com.example.bhagwadgeeta.ui.home.model.VerseItem
import com.example.bhagwadgeeta.utils.network.NetworkUtils
import com.example.bhagwadgeeta.utils.network.ResultOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

import kotlinx.coroutines.flow.Flow

class GeetaRepository @Inject constructor(
    private val geetaService: GeetaService,
    private val geetaDao: GeetaDao
) {
//    suspend fun getAllChapters(limit: Int): List<ChapterItem?>? {
//        return withContext(Dispatchers.IO) {
//            if (NetworkUtils.isNetworkAvailable()) {
////                try {
//                    val data = geetaService.getAllChapters(limit)
//                    // Update Room database here if necessary
//                    data
////                } catch (e: Exception) {
////                    ResultOf.Failure(e.message)
////                }
//            } else {
//                // Fetch data from Room database
//                val cachedData = geetaDao.getAllChapters()
////                if (cachedData.isNotEmpty()) {
//                    cachedData
////                } else {
////                    ResultOf.Failure("No data available")
////                }
//            }
//        }
//    }

    suspend fun getAllChapters(limit: Int): List<ChapterItem?>? {
        return geetaService.getAllChapters(limit)
    }

    fun getAllChaptersFromDatabase(): Flow<List<ChapterItem?>?> {
        return geetaDao.getAllChapters()
    }

    fun getFavoriteChapters(): Flow<List<ChapterItem?>?>  {
        return geetaDao.getFavoriteChapters()
    }

    fun updateChapterFavoriteStatus(chapterId: Int, isFavorite: Boolean) {
        geetaDao.updateChapterFavoriteStatus(chapterId, isFavorite)
    }

    fun addChaptersToDatabase(chapters: List<ChapterItem?>?) {
        geetaDao.insertOrUpdateChapters(chapters)
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