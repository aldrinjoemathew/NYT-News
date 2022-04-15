package com.nyt.nytnews.data.mapper

import com.nyt.nytnews.data.db.entities.NewsArticleDbEntity
import com.nyt.nytnews.data.network.models.HeadlineEntity
import com.nyt.nytnews.data.network.models.MultiMediaEntity
import com.nyt.nytnews.data.network.models.NewsArticleNwEntity
import com.nyt.nytnews.utils.EntityMapper

class NewsArticleDbMapper : EntityMapper<NewsArticleNwEntity, NewsArticleDbEntity> {
    override fun mapFromEntity(entity: NewsArticleNwEntity): NewsArticleDbEntity {
        return NewsArticleDbEntity(
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

    override fun mapToEntity(domain: NewsArticleDbEntity): NewsArticleNwEntity {
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

    fun mapFromEntities(entities: List<NewsArticleNwEntity>): List<NewsArticleDbEntity> {
        return entities.map { mapFromEntity(it) }
    }

    fun mapToEntities(domainModels: List<NewsArticleDbEntity>): List<NewsArticleNwEntity> {
        return domainModels.map { mapToEntity(it) }
    }
}