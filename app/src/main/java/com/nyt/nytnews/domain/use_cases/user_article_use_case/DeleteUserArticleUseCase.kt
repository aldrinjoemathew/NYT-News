package com.nyt.nytnews.domain.use_cases.user_article_use_case

import com.nyt.nytnews.domain.repository.NewsRepository
import javax.inject.Inject

class DeleteUserArticleUseCase @Inject constructor(private val newsRepository: NewsRepository) {

    operator fun invoke(articleId: String) {
        newsRepository.deleteArticle(articleId)
    }
}