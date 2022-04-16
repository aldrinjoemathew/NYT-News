package com.nyt.nytnews.models

import androidx.room.ColumnInfo

data class NewsArticle(
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "abstract")
    val abstractContent: String,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "lead_para")
    val leadContent: String,
    @ColumnInfo(name = "source")
    val newsSource: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String?,
    @ColumnInfo(name = "title")
    val headline: String,
    @ColumnInfo(name = "pub_date")
    val timestamp: Long
)