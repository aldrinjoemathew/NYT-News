package com.nyt.nytnews.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nyt.nytnews.data.db.entities.NewsArticleEntity
import com.nyt.nytnews.domain.models.NewsArticle

@Dao
interface NewsArticleDao {

    @Query("DELETE FROM news_articles")
    suspend fun clearRepos(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(articles: List<NewsArticleEntity>): Void

    @Query("SELECT id, abstract, url, lead_para, source, image_url, title, pub_date FROM news_articles ORDER BY `pub_date` DESC")
    fun newsArticles(): PagingSource<Int, NewsArticle>

    @Query("SELECT id, abstract, url, lead_para, source, image_url, title, pub_date FROM news_articles ORDER BY `pub_date` DESC")
    fun newsArticlesList(): List<NewsArticle>
}