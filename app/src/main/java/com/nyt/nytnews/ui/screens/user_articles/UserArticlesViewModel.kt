package com.nyt.nytnews.ui.screens.user_articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyt.nytnews.domain.models.NewsArticle
import com.nyt.nytnews.domain.use_cases.user_article_use_case.DeleteUserArticleUseCase
import com.nyt.nytnews.domain.use_cases.user_article_use_case.GetUserArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserArticlesViewModel @Inject constructor(
    private val getUserArticlesUseCase: GetUserArticlesUseCase,
    private val deleteUserArticleUseCase: DeleteUserArticleUseCase
): ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _userArticles = getUserArticlesUseCase()
    val userArticles = _userArticles

    fun deleteArticle(article: NewsArticle) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteUserArticleUseCase(articleId = article.id)
        }
    }
}