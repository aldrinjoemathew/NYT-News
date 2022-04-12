package com.nyt.nytnews.network.repository

import com.nyt.nytnews.models.NewsResponse

interface NewsRepository {
    suspend fun loadNews(pageCount: Int? = null, filter: String? = null, query: String? = null): NewsResponse
}