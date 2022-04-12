package com.nyt.nytnews.network.mapper

import com.nyt.nytnews.models.NewsArticle
import com.nyt.nytnews.network.models.HeadlineEntity
import com.nyt.nytnews.network.models.MultiMediaEntity
import com.nyt.nytnews.network.models.NewsArticleEntity
import com.nyt.nytnews.utils.EntityMapper

class NewsArticleMapper : EntityMapper<NewsArticleEntity, NewsArticle> {
    override fun mapFromEntity(entity: NewsArticleEntity): NewsArticle {
        return NewsArticle(
            abstractContent = entity.abstractContent,
            headline = entity.headline.mainHeadline,
            imageUrl = entity.images.firstOrNull()?.imageUrl?.let { url -> "https://www.nytimes.com/$url" },
            leadContent = entity.leadContent,
            newsSource = entity.newsSource,
            url = entity.url
        )
    }

    override fun mapToEntity(domain: NewsArticle): NewsArticleEntity {
        return NewsArticleEntity(
            url = domain.url,
            newsSource = domain.newsSource,
            leadContent = domain.leadContent,
            headline = HeadlineEntity(domain.headline),
            abstractContent = domain.abstractContent,
            images = domain.imageUrl?.let { listOf(MultiMediaEntity(domain.imageUrl)) }
                ?: emptyList()
        )
    }

    fun mapFromEntities(entities: List<NewsArticleEntity>): List<NewsArticle> {
        return entities.map { mapFromEntity(it) }
    }

    fun mapToEntities(domainModels: List<NewsArticle>): List<NewsArticleEntity> {
        return domainModels.map { mapToEntity(it) }
    }
}