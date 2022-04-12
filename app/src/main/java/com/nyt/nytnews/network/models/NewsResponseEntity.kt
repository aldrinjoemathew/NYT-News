package com.nyt.nytnews.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsResponseEntity(
    val status: String,
    val response: NewsResponseEnclosureEntity
)

@Serializable
data class NewsResponseEnclosureEntity(
    val meta: MetaData,
    @SerialName("docs") val articles: List<NewsArticleEntity>
)

@Serializable
data class MetaData(
    val hits: Int,
    val offset: Int
)