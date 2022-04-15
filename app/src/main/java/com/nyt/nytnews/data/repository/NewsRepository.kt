package com.nyt.nytnews.data.repository

import androidx.paging.PagingData
import com.nyt.nytnews.models.NewsArticle
import com.nyt.nytnews.models.NewsResponse
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun loadNews(
        pageCount: Int? = null,
        filter: String? = null,
        query: String? = null
    ): NewsResponse

    fun newsFlow(filter: String? = null, query: String? = null): Flow<PagingData<NewsArticle>>

    suspend fun popularArticles(): List<NewsArticle>
}