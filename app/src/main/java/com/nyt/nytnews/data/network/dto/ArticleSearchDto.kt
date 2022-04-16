package com.nyt.nytnews.data.network.dto

import com.nyt.nytnews.data.db.entities.NewsArticleEntity
import com.nyt.nytnews.domain.models.NewsArticle
import com.nyt.nytnews.domain.models.NewsResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ArticleSearchDto(
    @SerialName("copyright")
    val copyright: String,
    @SerialName("response")
    val response: ResponseDto,
    @SerialName("status")
    val status: String
)

@Serializable
data class ResponseDto(
    @SerialName("docs")
    val docs: List<ArticleDto>,
    @SerialName("meta")
    val meta: MetaDto
)

fun ArticleSearchDto.toArticles(): List<NewsArticle> {
    return response.docs.map {
        it.toNewsArticle()
    }
}

fun ArticleSearchDto.toNewsResponse(): NewsResponse {
    return NewsResponse(
        status = status,
        currentOffset = response.meta.offset,
        totalArticles = response.meta.hits,
        newsArticles = toArticles()
    )
}

fun ArticleSearchDto.toArticleEntities(): List<NewsArticleEntity> {
    return response.docs.map {
        it.toNewArticleEntity()
    }
}