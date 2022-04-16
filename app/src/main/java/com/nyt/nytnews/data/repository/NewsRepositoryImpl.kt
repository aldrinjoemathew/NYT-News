package com.nyt.nytnews.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nyt.nytnews.data.NewsResponseMediator
import com.nyt.nytnews.data.db.NytDatabase
import com.nyt.nytnews.data.network.NytApiService
import com.nyt.nytnews.data.network.dto.toNewsArticles
import com.nyt.nytnews.data.network.dto.toNewsResponse
import com.nyt.nytnews.domain.models.NewsArticle
import com.nyt.nytnews.domain.models.NewsResponse
import com.nyt.nytnews.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val apiService: NytApiService,
    private val database: NytDatabase
) : NewsRepository {
    override suspend fun loadNews(pageCount: Int?, filter: String?, query: String?): NewsResponse {
        val response = apiService.loadNews(pageKey = pageCount, filter = filter, query = query)
        return response.toNewsResponse()
    }

    override fun newsFlow(filter: String?, query: String?): Flow<PagingData<NewsArticle>> {
        val NETWORK_PAGE_SIZE = 10

        val pagingSourceFactory = {
            database.newsArticleDao().newsArticles()
        }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = true),
            remoteMediator = NewsResponseMediator(
                apiService,
                database,
                filter,
                query
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override suspend fun popularArticles(): List<NewsArticle> {
        val response = apiService.loadPopularArticles()
        return response.toNewsArticles()
    }
}