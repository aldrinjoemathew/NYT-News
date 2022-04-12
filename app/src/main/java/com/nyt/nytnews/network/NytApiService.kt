package com.nyt.nytnews.network

import com.nyt.nytnews.BuildConfig
import com.nyt.nytnews.network.models.NewsResponseEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface NytApiService {
    @GET(".")
    suspend fun loadNews(
        @Query(value = "page") pageKey: Int? = null,
        @Query(value = "fq") filter: String? = null,
        @Query(value = "q") query: String? = null,
        @Query(value = "api-key") apiKey: String = BuildConfig.API_KEY
    ): NewsResponseEntity
}