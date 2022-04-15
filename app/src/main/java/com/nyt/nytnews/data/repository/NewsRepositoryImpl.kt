package com.nyt.nytnews.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nyt.nytnews.models.NewsArticle
import com.nyt.nytnews.models.NewsResponse
import com.nyt.nytnews.data.network.NytApiService
import com.nyt.nytnews.data.network.mapper.NewsResponseMapper
import com.nyt.nytnews.data.network.mapper.PopularResponseMapper
import com.nyt.nytnews.data.NewsSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val apiService: NytApiService,
    private val mapper: NewsResponseMapper,
    private val popularResponseMapper: PopularResponseMapper
) : NewsRepository {
    override suspend fun loadNews(pageCount: Int?, filter: String?, query: String?): NewsResponse {
        val response = apiService.loadNews(pageKey = pageCount, filter = filter, query = query)
        return mapper.mapFromEntity(response)
    }

    override fun newsFlow(filter: String?, query: String?): Flow<PagingData<NewsArticle>> {
        val NETWORK_PAGE_SIZE = 10
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NewsSource(mapper, apiService, filter, query) }
        ).flow
    }

    override suspend fun popularArticles(): List<NewsArticle> {
        val response = apiService.loadPopularArticles()
        return popularResponseMapper.mapFromEntities(response.results)
    }
}