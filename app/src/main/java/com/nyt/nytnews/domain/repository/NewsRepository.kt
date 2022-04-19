package com.nyt.nytnews.domain.repository

import androidx.paging.PagingData
import com.nyt.nytnews.domain.models.NewsArticle
import com.nyt.nytnews.domain.models.NewsResponse
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun loadNews(
        pageCount: Int? = null,
        filter: String? = null,
        query: String? = null
    ): NewsResponse

    fun newsFlow(filter: String? = null, query: String? = null): Flow<PagingData<NewsArticle>>

    suspend fun popularArticles(): List<NewsArticle>

    suspend fun updateBookmark(articleId: String, isBookmarked: Boolean)

    fun getBookmarkedArticles(): Flow<List<NewsArticle>>

    suspend fun createUserArticle(title: String, abstractContent: String, content: String)

    fun getUserArticles(): Flow<List<NewsArticle>>

    fun deleteArticle(articleId: String)
}