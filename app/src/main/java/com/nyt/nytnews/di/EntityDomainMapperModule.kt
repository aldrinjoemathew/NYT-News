package com.nyt.nytnews.di

import com.nyt.nytnews.data.mapper.NewsArticleDbMapper
import com.nyt.nytnews.data.mapper.NewsArticleMapper
import com.nyt.nytnews.data.mapper.NewsResponseMapper
import com.nyt.nytnews.data.mapper.PopularResponseMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class EntityDomainMapperModule {
    @Provides
    fun provideNewsResponseMapper(newsArticleMapper: NewsArticleMapper): NewsResponseMapper {
        return NewsResponseMapper(newsArticleMapper)
    }

    @Provides
    fun provideNewsArticleMapper(): NewsArticleMapper {
        return NewsArticleMapper()
    }

    @Provides
    fun provideNewsArticleDbMapper(): NewsArticleDbMapper {
        return NewsArticleDbMapper()
    }

    @Provides
    fun providePopularArticleMapper(): PopularResponseMapper {
        return PopularResponseMapper()
    }
}