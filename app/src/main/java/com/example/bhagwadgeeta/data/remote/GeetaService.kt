package com.example.bhagwadgeeta.data.remote

import retrofit2.http.GET

interface GeetaService {

    @GET("")
    suspend fun getVerses()

}