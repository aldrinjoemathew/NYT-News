package com.nyt.nytnews.network.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nyt.nytnews.models.NewsArticle
import com.nyt.nytnews.network.NytApiService
import com.nyt.nytnews.network.mapper.NewsResponseMapper
import timber.log.Timber

class NewsSource(
    private val mapper: NewsResponseMapper,
    private val apiService: NytApiService,
    private val filter: String?,
    private val query: String?
) : PagingSource<Int, NewsArticle>() {

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, NewsArticle> {
        val nextPage = params.key ?: 1
        try {
            val news = apiService.loadNews(pageKey = nextPage, filter = filter, query = query)
            return if (news.status != "OK") {
                LoadResult.Error(Exception("Loading news failed"))
            } else {
                Timber.d("${mapper.mapFromEntity(news).newsArticles}")
                LoadResult.Page(
                    data = mapper.mapFromEntity(news).newsArticles,
                    prevKey =
                    if (nextPage == 1) null
                    else nextPage - 1,
                    nextKey = nextPage.plus(1)
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return LoadResult.Error(Exception("Loading news failed"))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, NewsArticle>): Int? {
        return null
    }
}