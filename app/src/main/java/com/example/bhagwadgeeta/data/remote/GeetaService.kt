package com.example.bhagwadgeeta.data.remote

import com.example.bhagwadgeeta.ui.home.model.ChapterItem
import com.example.bhagwadgeeta.ui.home.model.VerseItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GeetaService {

    @GET(GeetaEndpointKeys.GET_ALL_CHAPTERS)
    suspend fun getAllChapters(@Query("limit") limit: Int = 18): List<ChapterItem?>?

    @GET(GeetaEndpointKeys.GET_PARTICULAR_CHAPTER)
    suspend fun getParticularChapter(@Path("chapter_number") chapter_number: Int = 1): ChapterItem?

    @GET(GeetaEndpointKeys.GET_ALL_VERSES_FROM_CHAPTER)
    suspend fun getAllVersesFromChapter(@Path("chapter_number") chapter_number: Int): List<VerseItem?>?

    @GET(GeetaEndpointKeys.GET_PARTICULAR_VERSE)
    suspend fun getParticularVerse(
        @Path("chapter_number") chapter_number: Int,
        @Path("verse_number") verse_number: Int
    ): VerseItem?

}