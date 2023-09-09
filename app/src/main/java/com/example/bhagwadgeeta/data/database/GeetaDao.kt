package com.example.bhagwadgeeta.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bhagwadgeeta.data.database.Temp
import kotlinx.coroutines.flow.Flow

@Dao
interface GeetaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExpression(temp: Temp)

    @Query("SELECT * FROM geeta_database ORDER BY name DESC")
    fun getAllExpressions(): Flow<List<Temp>>
}
