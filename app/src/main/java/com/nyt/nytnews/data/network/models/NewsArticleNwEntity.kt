package com.nyt.nytnews.data.network.models

import com.nyt.nytnews.data.LongTimestampToMillisSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsArticleNwEntity(
    @SerialName("_id") val id: String,
    @SerialName("abstract") val abstractContent: String,
    @SerialName("web_url") val url: String,
    @SerialName("lead_paragraph") val leadContent: String,
    @SerialName("source") val newsSource: String = "",
    @SerialName("multimedia") val images: List<MultiMediaEntity>,
    @SerialName("headline") val headline: HeadlineEntity,
    @SerialName("pub_date")
    @Serializable(with = LongTimestampToMillisSerializer::class) val timestamp: Long
)

@Serializable
data class MultiMediaEntity(
    @SerialName("url") val imageUrl: String
)

@Serializable
data class HeadlineEntity(
    @SerialName("main") val mainHeadline: String
)