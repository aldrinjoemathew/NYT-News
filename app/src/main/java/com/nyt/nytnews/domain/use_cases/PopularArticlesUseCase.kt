package com.nyt.nytnews.domain.use_cases

import com.nyt.nytnews.domain.models.NewsArticle
import com.nyt.nytnews.domain.repository.NewsRepository
import javax.inject.Inject

class PopularArticlesUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    suspend operator fun invoke(): List<NewsArticle> {
        return newsRepository.popularArticles()
    }
}