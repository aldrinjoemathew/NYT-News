package com.nyt.nytnews.models

data class NewsResponse(
    val status: String,
    val newsArticles: List<NewsArticle>,
    val totalArticles: Int,
    val currentOffset: Int
)