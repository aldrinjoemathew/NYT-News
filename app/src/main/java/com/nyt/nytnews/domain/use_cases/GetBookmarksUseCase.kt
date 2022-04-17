package com.nyt.nytnews.domain.use_cases

import com.nyt.nytnews.domain.models.NewsArticle
import com.nyt.nytnews.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookmarksUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    operator fun invoke(): Flow<List<NewsArticle>> {
        return newsRepository.getBookmarkedArticles()
    }
}