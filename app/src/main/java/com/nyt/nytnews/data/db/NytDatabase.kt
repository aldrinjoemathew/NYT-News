package com.nyt.nytnews.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nyt.nytnews.data.db.dao.NewsArticleDao
import com.nyt.nytnews.data.db.dao.RemoteKeysDao
import com.nyt.nytnews.data.db.dao.UserDao
import com.nyt.nytnews.data.db.entities.ArticleTypeConverter
import com.nyt.nytnews.data.db.entities.NewsArticleEntity
import com.nyt.nytnews.data.db.entities.RemoteKeyEntity
import com.nyt.nytnews.data.db.entities.UserEntity

@Database(
    entities = [
        UserEntity::class,
        NewsArticleEntity::class,
        RemoteKeyEntity::class
    ],
    version = 8,
    exportSchema = false
)
@TypeConverters(ArticleTypeConverter::class)
abstract class NytDatabase : RoomDatabase() {
    companion object {
        fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                NytDatabase::class.java,
                "Nyt_news.db"
            ).allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
    }

    abstract fun userDao(): UserDao
    abstract fun newsArticleDao(): NewsArticleDao
    abstract fun remoteKeysDao(): RemoteKeysDao

}