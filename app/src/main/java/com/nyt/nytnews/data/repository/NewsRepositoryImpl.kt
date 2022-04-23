package com.nyt.nytnews.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nyt.nytnews.data.NewsResponseMediator
import com.nyt.nytnews.data.db.NytDatabase
import com.nyt.nytnews.data.db.entities.ArticleType
import com.nyt.nytnews.data.db.entities.NewsArticleEntity
import com.nyt.nytnews.data.network.NytApiService
import com.nyt.nytnews.data.network.dto.toNewsArticles
import com.nyt.nytnews.data.network.dto.toNewsResponse
import com.nyt.nytnews.domain.models.NewsArticle
import com.nyt.nytnews.domain.models.NewsResponse
import com.nyt.nytnews.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import java.util.*
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

    override suspend fun updateBookmark(articleId: String, isBookmarked: Boolean) {
        database.newsArticleDao().updateBookmark(articleId, isBookmarked)
    }

    override fun getBookmarkedArticles(): Flow<List<NewsArticle>> {
        return database.newsArticleDao().fetchFavorites()
    }

    override suspend fun createUserArticle(
        title: String,
        abstractContent: String,
        content: String
    ) {
        val newArticle = NewsArticleEntity(
            isBookmarked = false,
            articleType = ArticleType.UserArticle,
            timestamp = System.currentTimeMillis(),
            imageUrl = "",
            id = UUID.randomUUID().toString(),
            abstractContent = abstractContent,
            headline = title,
            leadContent = content,
            newsSource = "",
            url = ""
        )
        database.newsArticleDao().insertArticle(newArticle)
    }

    override fun getUserArticles(): Flow<List<NewsArticle>> {
        return database.newsArticleDao().getUserArticles()
    }

    override fun deleteArticle(articleId: String) {
        database.newsArticleDao().deleteArticle(articleId)
    }
}