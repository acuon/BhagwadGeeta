package com.example.bhagwadgeeta.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bhagwadgeeta.ui.home.model.ChapterItem

@Database(entities = [ChapterItem::class], version = 1, exportSchema = false)
abstract class GeetaDatabase : RoomDatabase() {
    abstract fun mathExpressionDao(): GeetaDao
}
