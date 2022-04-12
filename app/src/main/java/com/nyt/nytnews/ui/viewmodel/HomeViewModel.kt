package com.nyt.nytnews.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyt.nytnews.models.NewsArticle
import com.nyt.nytnews.network.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val newsRepository: NewsRepository): ViewModel() {

    private val _newsArticles =  MutableStateFlow<List<NewsArticle>>(emptyList())
    val newsArticles = _newsArticles.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val response = newsRepository.loadNews(1)
                Timber.d(response.toString())
                _newsArticles.update { response.newsArticles }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}