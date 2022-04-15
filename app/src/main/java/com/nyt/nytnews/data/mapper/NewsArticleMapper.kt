package com.nyt.nytnews.data.mapper

import com.nyt.nytnews.data.network.models.HeadlineEntity
import com.nyt.nytnews.data.network.models.MultiMediaEntity
import com.nyt.nytnews.data.network.models.NewsArticleNwEntity
import com.nyt.nytnews.models.NewsArticle
import com.nyt.nytnews.utils.EntityMapper

class NewsArticleMapper : EntityMapper<NewsArticleNwEntity, NewsArticle> {
    override fun mapFromEntity(entity: NewsArticleNwEntity): NewsArticle {
        return NewsArticle(
            id = entity.id,
            abstractContent = entity.abstractContent,
            headline = entity.headline.mainHeadline,
            imageUrl = entity.images.firstOrNull()?.imageUrl?.let { url -> "https://www.nytimes.com/$url" },
            leadContent = entity.leadContent,
            newsSource = entity.newsSource,
            url = entity.url,
            timestamp = entity.timestamp
        )
    }

    override fun mapToEntity(domain: NewsArticle): NewsArticleNwEntity {
        return NewsArticleNwEntity(
            id = domain.id,
            url = domain.url,
            newsSource = domain.newsSource,
            leadContent = domain.leadContent,
            headline = HeadlineEntity(domain.headline),
            abstractContent = domain.abstractContent,
            images = domain.imageUrl?.let { listOf(MultiMediaEntity(domain.imageUrl)) }
                ?: emptyList(),
            timestamp = domain.timestamp
        )
    }

    fun mapFromEntities(entities: List<NewsArticleNwEntity>): List<NewsArticle> {
        return entities.map { mapFromEntity(it) }
    }

    fun mapToEntities(domainModels: List<NewsArticle>): List<NewsArticleNwEntity> {
        return domainModels.map { mapToEntity(it) }
    }
}