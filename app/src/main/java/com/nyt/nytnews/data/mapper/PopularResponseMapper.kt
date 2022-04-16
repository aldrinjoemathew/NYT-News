package com.nyt.nytnews.data.mapper

import com.nyt.nytnews.data.network.models.PopularArticle
import com.nyt.nytnews.models.NewsArticle
import com.nyt.nytnews.utils.EntityMapper

class PopularResponseMapper : EntityMapper<PopularArticle, NewsArticle> {
    override fun mapFromEntity(entity: PopularArticle): NewsArticle {
        return NewsArticle(
            id = entity.id,
            abstractContent = entity.abstract,
            headline = entity.title,
            imageUrl = entity.media?.firstOrNull { it.type == "image" || it.subtype == "photo" }
                ?.mediaMetadata?.maxByOrNull { it.width }?.url ?: "",
            leadContent = entity.abstract,
            newsSource = entity.source,
            url = entity.url,
            timestamp = entity.publishedDate
        )
    }

    override fun mapToEntity(domain: NewsArticle): PopularArticle? {
        return null
    }

    fun mapFromEntities(entities: List<PopularArticle>): List<NewsArticle> {
        return entities.map { mapFromEntity(it) }
    }
}