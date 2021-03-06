package com.nyt.nytnews.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nyt.nytnews.data.db.entities.ArticleType
import com.nyt.nytnews.data.db.entities.NewsArticleEntity
import com.nyt.nytnews.domain.models.NewsArticle
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsArticleDao {

    @Query("DELETE FROM news_articles")
    suspend fun deleteAll(): Int

    @Query("DELETE FROM news_articles WHERE bookmark == :bookmark AND article_type != :articleType")
    suspend fun deleteNonBookmarkedNonUserArticles(
        bookmark: Boolean = false,
        articleType: ArticleType = ArticleType.UserArticle
    ): Int

    @Query("UPDATE news_articles SET article_type = :articleTypeTo WHERE article_type == :articleTypeFrom")
    suspend fun updateRemoteArticlesAsLocalCopy(
        articleTypeFrom: ArticleType = ArticleType.NetworkData,
        articleTypeTo: ArticleType = ArticleType.LocalCopy,
    )

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(articles: List<NewsArticleEntity>): Void

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: NewsArticleEntity): Void

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun replaceAll(articles: List<NewsArticleEntity>): Void

    @Query("SELECT id, abstract, url, lead_para, source, image_url, title, pub_date, bookmark, article_type FROM news_articles WHERE article_type == :articleType ORDER BY `pub_date` DESC")
    fun newsArticles(articleType: ArticleType = ArticleType.NetworkData): PagingSource<Int, NewsArticle>

    @Query("SELECT id, abstract, url, lead_para, source, image_url, title, pub_date, bookmark, article_type FROM news_articles ORDER BY `pub_date` DESC")
    fun newsArticlesList(): List<NewsArticle>

    @Query("SELECT * FROM news_articles ORDER BY `pub_date` DESC")
    fun fetchAllArticles(): List<NewsArticleEntity>

    @Query("UPDATE news_articles SET bookmark = :bookmarked WHERE id == :articleId")
    fun updateBookmark(articleId: String, bookmarked: Boolean)

    @Query("SELECT id, abstract, url, lead_para, source, image_url, title, pub_date, bookmark, article_type FROM news_articles WHERE bookmark == :bookmark ORDER BY `pub_date` DESC")
    fun fetchFavorites(bookmark: Boolean = true): Flow<List<NewsArticle>>

    @Query(
        "SELECT id, abstract, url, lead_para, source, image_url, title, pub_date, bookmark, article_type " +
                "FROM news_articles WHERE article_type == :articleType ORDER BY `pub_date` DESC"
    )
    fun getUserArticles(articleType: ArticleType = ArticleType.UserArticle): Flow<List<NewsArticle>>

    @Query("DELETE FROM news_articles WHERE id == :articleId")
    fun deleteArticle(articleId: String)
}