package com.android.goodanime.common.api

import com.android.goodanime.common.api.response.GAAnimeDetailsResponse
import com.android.goodanime.common.api.response.GATopAnimeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * The API service for the GoodAnime
 */
interface GAApiService {
    @GET("top/anime")
    suspend fun getTopAnime(
        @Query("page") page: Int,
    ): Response<GATopAnimeResponse>

    @GET("anime/{anime_id}")
    suspend fun getAnimeDetails(@Path("anime_id") animeId: Int): Response<GAAnimeDetailsResponse>
}