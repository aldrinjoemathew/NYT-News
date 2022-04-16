package com.nyt.nytnews.domain.use_cases

import androidx.paging.PagingData
import com.nyt.nytnews.domain.models.NewsArticle
import com.nyt.nytnews.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArticleSearchUseCase @Inject constructor(private val newsRepository: NewsRepository) {

    operator fun invoke(
        filter: String? = null,
        query: String? = null
    ): Flow<PagingData<NewsArticle>> {
        return newsRepository.newsFlow(filter = filter, query = query)
    }
}