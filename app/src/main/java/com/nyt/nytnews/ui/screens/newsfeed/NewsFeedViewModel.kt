package com.nyt.nytnews.ui.screens.newsfeed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.nyt.nytnews.domain.models.NewsArticle
import com.nyt.nytnews.domain.use_cases.ArticleSearchUseCase
import com.nyt.nytnews.domain.use_cases.PopularArticlesUseCase
import com.nyt.nytnews.domain.use_cases.UpdateBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    private val articleSearchUseCase: ArticleSearchUseCase,
    private val popularArticlesUseCase: PopularArticlesUseCase,
    private val updateBookmarkUseCase: UpdateBookmarkUseCase
) :
    ViewModel() {

    var filter = MutableStateFlow("")

    private val _isRefreshing = MutableStateFlow(false)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _newsArticles = filter.flatMapLatest {
        articleSearchUseCase(filter = it)
    }.cachedIn(viewModelScope)
    val newsArticles = _newsArticles

    private val _popularArticles: MutableStateFlow<List<NewsArticle>> =
        MutableStateFlow(emptyList())
    val popularArticles = _popularArticles.asStateFlow()

    fun updateFilter(filter: String) {
        this.filter.update { filter }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _isRefreshing.collectLatest { refreshing ->
                if (refreshing) loadPopularArticles()
            }
        }
    }

    fun updateRefreshing(isRefreshing: Boolean) {
        _isRefreshing.update { isRefreshing }
    }

    private fun loadPopularArticles() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val popularArticles = popularArticlesUseCase()
                _popularArticles.update { popularArticles }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun toggleFavorite(article: NewsArticle) {
        viewModelScope.launch(Dispatchers.IO) {
            updateBookmarkUseCase(articleId = article.id, isBookmarked = !article.isBookmarked)
        }
    }
}