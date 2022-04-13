package com.nyt.nytnews.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.nyt.nytnews.models.NewsArticle
import com.nyt.nytnews.network.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(private val newsRepository: NewsRepository) :
    ViewModel() {

    var filter = MutableStateFlow("")

    private val _newsArticles = filter.flatMapLatest {
        newsRepository.newsFlow(filter = it).cachedIn(viewModelScope)
    }
    val newsArticles = _newsArticles

    private val _popularArticles: MutableStateFlow<List<NewsArticle>> =
        MutableStateFlow(emptyList())
    val popularArticles = _popularArticles.asStateFlow()

    fun updateFilter(filter: String) {
        this.filter.update { filter }
    }

    init {
        loadPopularArticles()
    }

    fun loadPopularArticles() {
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