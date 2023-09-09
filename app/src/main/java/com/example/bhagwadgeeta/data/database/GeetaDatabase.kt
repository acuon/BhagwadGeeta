package com.example.bhagwadgeeta.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bhagwadgeeta.data.database.GeetaDao
import com.example.bhagwadgeeta.data.database.Temp

@Database(entities = [Temp::class], version = 1, exportSchema = false)
abstract class GeetaDatabase : RoomDatabase() {
    abstract fun mathExpressionDao(): GeetaDao
}
