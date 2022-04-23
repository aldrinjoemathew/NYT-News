package com.nyt.nytnews.domain.use_cases.user_article_use_case

import com.nyt.nytnews.domain.models.NewsArticle
import com.nyt.nytnews.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserArticlesUseCase @Inject constructor(private val newsRepository: NewsRepository) {

    operator fun invoke(): Flow<List<NewsArticle>> {
        return newsRepository.getUserArticles()
    }
}