package com.nyt.nytnews.data.network

import com.nyt.nytnews.BuildConfig
import com.nyt.nytnews.data.network.dto.ArticleSearchDto
import com.nyt.nytnews.data.network.dto.PopularArticleSearchDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NytApiService {
    @GET("search/v2/articlesearch.json")
    suspend fun loadNews(
        @Query(value = "page") pageKey: Int? = null,
        @Query(value = "fq") filter: String? = null,
        @Query(value = "q") query: String? = null,
        @Query(value = "api-key") apiKey: String = BuildConfig.API_KEY
    ): ArticleSearchDto

    @GET("mostpopular/v2/viewed/1.json")
    suspend fun loadPopularArticles(
        @Query(value = "api-key") apiKey: String = BuildConfig.API_KEY
    ): PopularArticleSearchDto
}