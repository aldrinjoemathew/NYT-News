package com.nyt.nytnews.domain.use_cases.user_article_use_case

import com.nyt.nytnews.domain.repository.NewsRepository
import javax.inject.Inject

class CreateUserArticleUseCase @Inject constructor(private val newsRepository: NewsRepository) {

    suspend operator fun invoke(title: String, abstractContent: String, content: String) {
        newsRepository.createUserArticle(title, abstractContent, content)
    }
}