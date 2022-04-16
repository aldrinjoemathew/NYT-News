package com.nyt.nytnews.ui.screens.newsfeed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.nyt.nytnews.models.NewsArticle
import com.nyt.nytnews.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(private val newsRepository: NewsRepository) :
    ViewModel() {

    var filter = MutableStateFlow("")

    private val _isRefreshing = MutableStateFlow(false)

    private val _newsArticles = filter.flatMapLatest {
        newsRepository.newsFlow(filter = it)
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
        viewModelScope.launch(Dispatchers.IO) {
            newsRepository.newsFlow().map {
                it.map {
                    Timber.d("${it.timestamp} ${it.headline}")
                }
            }.shareIn(viewModelScope, SharingStarted.Eagerly).collect()
        }
    }

    fun updateRefreshing(isRefreshing: Boolean) {
        _isRefreshing.update { isRefreshing }
    }

    private fun loadPopularArticles() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val popularArticles = newsRepository.popularArticles()
                _popularArticles.update { popularArticles }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}