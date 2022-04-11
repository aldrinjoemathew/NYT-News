package com.nyt.nytnews.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nyt.nytnews.db.dao.UserDao
import com.nyt.nytnews.db.entities.UserEntity

@Database(
    entities = [
        UserEntity::class
    ],
    version = 2,
    exportSchema = false
)
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

}