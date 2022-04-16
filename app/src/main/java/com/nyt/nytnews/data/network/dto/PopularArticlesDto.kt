package com.nyt.nytnews.data.network.dto

import com.nyt.nytnews.data.LongToStringSerializer
import com.nyt.nytnews.data.ShortTimestampToMillisSerializer
import com.nyt.nytnews.domain.models.NewsArticle
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class PopularArticleSearchDto(
    @SerialName("copyright")
    val copyright: String,
    @SerialName("num_results")
    val numResults: Int,
    @SerialName("results")
    val results: List<PopularArticleDto>,
    @SerialName("status")
    val status: String
)

@Serializable
data class PopularArticleDto(
    @SerialName("abstract")
    val `abstract`: String,
    @SerialName("adx_keywords")
    val adxKeywords: String,
    @SerialName("asset_id")
    val assetId: Long,
    @SerialName("byline")
    val byline: String,
    @SerialName("des_facet")
    val desFacet: List<String>,
    @SerialName("eta_id")
    val etaId: Int,
    @SerialName("geo_facet")
    val geoFacet: List<String>,
    @SerialName("id")
    @Serializable(with = LongToStringSerializer::class)
    val id: String,
    @SerialName("media")
    val media: List<Media>?,
    @SerialName("nytdsection")
    val nytdsection: String,
    @SerialName("org_facet")
    val orgFacet: List<String>,
    @SerialName("per_facet")
    val perFacet: List<String>,
    @SerialName("published_date")
    @Serializable(with = ShortTimestampToMillisSerializer::class)
    val publishedDate: Long,
    @SerialName("section")
    val section: String,
    @SerialName("source")
    val source: String,
    @SerialName("subsection")
    val subsection: String,
    @SerialName("title")
    val title: String,
    @SerialName("type")
    val type: String,
    @SerialName("updated")
    val updated: String,
    @SerialName("uri")
    val uri: String,
    @SerialName("url")
    val url: String
)

@Serializable
data class Media(
    @SerialName("approved_for_syndication")
    val approvedForSyndication: Int,
    @SerialName("caption")
    val caption: String,
    @SerialName("copyright")
    val copyright: String,
    @SerialName("media-metadata")
    val mediaMetadata: List<MediaMetadata>,
    @SerialName("subtype")
    val subtype: String,
    @SerialName("type")
    val type: String
)

@Serializable
data class MediaMetadata(
    @SerialName("format")
    val format: String,
    @SerialName("height")
    val height: Int,
    @SerialName("url")
    val url: String,
    @SerialName("width")
    val width: Int
)

fun PopularArticleDto.toNewsArticle(): NewsArticle {
    return NewsArticle(
        id = id,
        abstractContent = abstract,
        headline = title,
        imageUrl = media?.firstOrNull { it.type == "image" || it.subtype == "photo" }
            ?.mediaMetadata?.maxByOrNull { it.width }?.url ?: "",
        leadContent = abstract,
        newsSource = source,
        url = url,
        timestamp = publishedDate
    )
}

fun PopularArticleSearchDto.toNewsArticles(): List<NewsArticle> {
    return results.map {
        it.toNewsArticle()
    }
}