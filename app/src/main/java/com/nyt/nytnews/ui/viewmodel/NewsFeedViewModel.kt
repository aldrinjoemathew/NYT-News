package com.nyt.nytnews.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.nyt.nytnews.network.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {

    var filter = MutableStateFlow("")

    private val _newsArticles = filter.flatMapLatest {
        newsRepository.newsFlow(filter = it).cachedIn(viewModelScope)
    }
    val newsArticles = _newsArticles

    init {

    }

    fun updateFilter(filter: String) {
        this.filter.update { filter }
    }
}