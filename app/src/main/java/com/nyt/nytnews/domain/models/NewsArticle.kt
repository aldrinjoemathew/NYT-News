package com.nyt.nytnews.domain.models

import androidx.room.ColumnInfo
import com.nyt.nytnews.data.db.entities.ArticleType

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
    val timestamp: Long,
    @ColumnInfo(name = "bookmark")
    val isBookmarked: Boolean,
    @ColumnInfo(name = "article_type")
    val articleType: ArticleType
)