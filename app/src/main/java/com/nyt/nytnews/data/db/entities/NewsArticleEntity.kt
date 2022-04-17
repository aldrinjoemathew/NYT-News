package com.nyt.nytnews.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "news_articles")
data class NewsArticleEntity(
    @PrimaryKey
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

enum class ArticleType {
    LocalCopy, NetworkData
}

class ArticleTypeConverter {

    @TypeConverter
    fun fromPriority(articleType: ArticleType): String {
        return articleType.name
    }

    @TypeConverter
    fun toPriority(articleType: String): ArticleType {
        return ArticleType.valueOf(articleType)
    }

}
