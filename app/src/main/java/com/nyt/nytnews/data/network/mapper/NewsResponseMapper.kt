package com.nyt.nytnews.data.network.mapper

import com.nyt.nytnews.models.NewsResponse
import com.nyt.nytnews.data.network.models.MetaData
import com.nyt.nytnews.data.network.models.NewsResponseEnclosureEntity
import com.nyt.nytnews.data.network.models.NewsResponseEntity
import com.nyt.nytnews.utils.EntityMapper
import javax.inject.Inject

class NewsResponseMapper @Inject constructor(private val newsArticleMapper: NewsArticleMapper) :
    EntityMapper<NewsResponseEntity, NewsResponse> {
    override fun mapFromEntity(entity: NewsResponseEntity): NewsResponse {
        return NewsResponse(
            status = entity.status,
            currentOffset = entity.response.meta.offset,
            totalArticles = entity.response.meta.hits,
            newsArticles = newsArticleMapper.mapFromEntities(entity.response.articles)
        )
    }

    override fun mapToEntity(domain: NewsResponse): NewsResponseEntity {
        return NewsResponseEntity(
            status = domain.status,
            response = NewsResponseEnclosureEntity(
                articles = newsArticleMapper.mapToEntities(domain.newsArticles),
                meta = MetaData(hits = domain.totalArticles, offset = domain.currentOffset)
            ),
        )
    }
}