package com.nyt.nytnews.ui.screens.new_article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyt.nytnews.domain.use_cases.user_article_use_case.CreateUserArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewArticleViewModel @Inject constructor(
    private val createUserArticleUseCase: CreateUserArticleUseCase
) : ViewModel() {

    enum class ArticleCreationProgress {
        Pending, Loading, Error, Complete
    }

    private val _createArticleInProgress = MutableStateFlow(ArticleCreationProgress.Pending)
    val createArticleInProgress = _createArticleInProgress

    fun createArticle(title: String, abstractContent: String, content: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _createArticleInProgress.update { ArticleCreationProgress.Loading }
            try {
                createUserArticleUseCase(title, abstractContent, content)
                _createArticleInProgress.update { ArticleCreationProgress.Complete }
            } catch (e: Exception) {
                e.printStackTrace()
                _createArticleInProgress.update { ArticleCreationProgress.Error }
            }
        }
    }
}