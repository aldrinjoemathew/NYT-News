package com.nyt.nytnews.di

import android.content.Context
import com.nyt.nytnews.db.NytDatabase
import com.nyt.nytnews.db.dao.UserDao
import com.nyt.nytnews.db.repository.UserRepo
import com.nyt.nytnews.db.repository.UserRepoImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideUserDao(appDatabase: NytDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): NytDatabase {
        return NytDatabase.buildDatabase(appContext)
    }
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun providesUserRepository(userRepoImpl: UserRepoImpl): UserRepo
}