package com.nyt.nytnews.di

import com.nyt.nytnews.data.repository.NewsRepositoryImpl
import com.nyt.nytnews.data.repository.UserRepoImpl
import com.nyt.nytnews.domain.repository.NewsRepository
import com.nyt.nytnews.domain.repository.UserRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun providesUserRepository(userRepoImpl: UserRepoImpl): UserRepo

    @Binds
    abstract fun providesNewsRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository
}