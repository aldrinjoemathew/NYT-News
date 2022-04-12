package com.nyt.nytnews.models

data class NewsArticle(
    val abstractContent: String,
    val url: String,
    val leadContent: String,
    val newsSource: String,
    val imageUrl: String?,
    val headline: String,
)