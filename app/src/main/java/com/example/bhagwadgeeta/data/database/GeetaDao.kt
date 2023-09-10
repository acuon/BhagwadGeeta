package com.example.bhagwadgeeta.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.bhagwadgeeta.ui.home.model.ChapterItem
import kotlinx.coroutines.flow.Flow

@Dao
interface GeetaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChapters(chapters: List<ChapterItem?>?)

    // Custom insert method
    @Transaction
    fun insertOrUpdateChapters(chapters: List<ChapterItem?>?) {
        chapters?.forEach { newChapter ->
            val existingChapter = getChapterById(newChapter?.id!!)

            if (existingChapter != null) {
                // Check if the existing chapter is marked as a favorite
                val isFavorite = existingChapter.favorite

                // Update the new chapter's favorite status accordingly
                newChapter.favorite = isFavorite
            }

            // Insert or update the chapter
            insertChapters(listOf(newChapter))
        }
    }

    @Query("SELECT * FROM geeta_database WHERE id = :chapterId")
    fun getChapterById(chapterId: Int): ChapterItem?

    @Query("SELECT * FROM geeta_database ORDER BY name DESC")
    fun getAllChapters(): Flow<List<ChapterItem>>

    @Query("SELECT * FROM geeta_database WHERE favorite == 1")
    fun getFavoriteChapters(): Flow<List<ChapterItem>>

    @Query("UPDATE geeta_database SET favorite = :isFavorite WHERE id = :chapterId")
    fun updateChapterFavoriteStatus(chapterId: Int, isFavorite: Boolean)
}