package com.example.bhagwadgeeta.data.repository

import com.example.bhagwadgeeta.data.database.GeetaDao
import com.example.bhagwadgeeta.data.database.Temp
import com.example.bhagwadgeeta.data.remote.GeetaService
import javax.inject.Inject

class GeetaRepository @Inject constructor(
    private val geetaService: GeetaService,
    private val geetaDao: GeetaDao
) {

    suspend fun getAllChapters(): Temp {
        geetaService.getAllVersesFromChapter(1)
        return Temp("")
    }

}