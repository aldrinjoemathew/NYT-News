package com.nyt.nytnews.domain.use_cases

import com.nyt.nytnews.domain.repository.NewsRepository
import javax.inject.Inject

class UpdateBookmarkUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    suspend operator fun invoke(articleId: String, isBookmarked: Boolean) {
        newsRepository.updateBookmark(articleId, isBookmarked)
    }
}