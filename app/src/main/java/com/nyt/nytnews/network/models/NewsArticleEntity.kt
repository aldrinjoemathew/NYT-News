package com.nyt.nytnews.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsArticleEntity(
    @SerialName("abstract") val abstractContent: String,
    @SerialName("web_url") val url: String,
    @SerialName("lead_paragraph") val leadContent: String,
    @SerialName("source") val newsSource: String = "",
    @SerialName("multimedia") val images: List<MultiMediaEntity>,
    @SerialName("headline") val headline: HeadlineEntity,
)

@Serializable
data class MultiMediaEntity(
    @SerialName("url") val imageUrl: String
)

@Serializable
data class HeadlineEntity(
    @SerialName("main") val mainHeadline: String
)