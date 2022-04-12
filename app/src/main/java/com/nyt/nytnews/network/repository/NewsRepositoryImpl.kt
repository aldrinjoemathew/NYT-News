package com.nyt.nytnews.network.repository

import com.nyt.nytnews.models.NewsResponse
import com.nyt.nytnews.network.NytApiService
import com.nyt.nytnews.network.mapper.NewsResponseMapper
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val apiService: NytApiService,
    private val mapper: NewsResponseMapper
) : NewsRepository {
    override suspend fun loadNews(pageCount: Int?, filter: String?, query: String?): NewsResponse {
        val response = apiService.loadNews(pageKey = pageCount, filter = filter, query = query)
        return mapper.mapFromEntity(response)
    }
}