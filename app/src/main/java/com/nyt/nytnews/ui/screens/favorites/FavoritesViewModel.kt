package com.nyt.nytnews.ui.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyt.nytnews.domain.models.NewsArticle
import com.nyt.nytnews.domain.use_cases.GetBookmarksUseCase
import com.nyt.nytnews.domain.use_cases.UpdateBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    getBookmarksUseCase: GetBookmarksUseCase,
    private val updateBookmarkUseCase: UpdateBookmarkUseCase
) :
    ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _favArticles = getBookmarksUseCase()
    val favArticles = _favArticles

    fun toggleFavorite(article: NewsArticle) {
        viewModelScope.launch(Dispatchers.IO) {
            updateBookmarkUseCase(articleId = article.id, isBookmarked = !article.isBookmarked)
        }
    }

}