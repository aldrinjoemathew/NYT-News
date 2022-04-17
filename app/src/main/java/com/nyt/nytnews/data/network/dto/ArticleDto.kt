package com.nyt.nytnews.data.network.dto

import com.nyt.nytnews.data.LongTimestampToMillisSerializer
import com.nyt.nytnews.data.db.entities.ArticleType
import com.nyt.nytnews.data.db.entities.NewsArticleEntity
import com.nyt.nytnews.domain.models.NewsArticle
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleDto(
    @SerialName("abstract")
    val abstractContent: String,
    @SerialName("byline")
    val byline: BylineDto,
    @SerialName("document_type")
    val documentType: String,
    @SerialName("headline")
    val headline: HeadlineDto,
    @SerialName("_id")
    val id: String,
    @SerialName("keywords")
    val keywords: List<KeywordDto>,
    @SerialName("lead_paragraph")
    val leadParagraph: String,
    @SerialName("multimedia")
    val multimedia: List<MultimediaDto>,
    @SerialName("news_desk")
    val newsDesk: String,
    @SerialName("print_page")
    val printPage: String? = null,
    @SerialName("print_section")
    val printSection: String? = null,
    @SerialName("pub_date")
    @Serializable(with = LongTimestampToMillisSerializer::class)
    val pubDate: Long,
    @SerialName("section_name")
    val sectionName: String,
    @SerialName("snippet")
    val snippet: String,
    @SerialName("source")
    val source: String,
    @SerialName("subsection_name")
    val subsectionName: String? = null,
    @SerialName("type_of_material")
    val typeOfMaterial: String,
    @SerialName("uri")
    val uri: String,
    @SerialName("web_url")
    val webUrl: String,
    @SerialName("word_count")
    val wordCount: Int
)

@Serializable
data class MetaDto(
    @SerialName("hits")
    val hits: Int,
    @SerialName("offset")
    val offset: Int,
    @SerialName("time")
    val time: Int
)

@Serializable
data class BylineDto(
    @SerialName("original")
    val original: String? = null,
    @SerialName("person")
    val person: List<PersonDto>
)

@Serializable
data class HeadlineDto(
    @SerialName("main")
    val main: String,
)

@Serializable
data class KeywordDto(
    @SerialName("major")
    val major: String,
    @SerialName("name")
    val name: String,
    @SerialName("rank")
    val rank: Int,
    @SerialName("value")
    val value: String
)

@Serializable
data class MultimediaDto(
    @SerialName("crop_name")
    val cropName: String,
    @SerialName("height")
    val height: Int,
    @SerialName("legacy")
    val legacy: LegacyDto,
    @SerialName("rank")
    val rank: Int,
    @SerialName("subType")
    val subType: String,
    @SerialName("subtype")
    val subtype: String,
    @SerialName("type")
    val type: String,
    @SerialName("url")
    val url: String,
    @SerialName("width")
    val width: Int
)

@Serializable
data class PersonDto(
    @SerialName("firstname")
    val firstname: String,
    @SerialName("lastname")
    val lastname: String? = null,
    @SerialName("organization")
    val organization: String,
    @SerialName("rank")
    val rank: Int,
    @SerialName("role")
    val role: String,
)

@Serializable
data class LegacyDto(
    @SerialName("thumbnail")
    val thumbnail: String? = null,
    @SerialName("thumbnailheight")
    val thumbnailheight: Int = 0,
    @SerialName("thumbnailwidth")
    val thumbnailwidth: Int = 0,
    @SerialName("wide")
    val wide: String? = null,
    @SerialName("wideheight")
    val wideheight: Int = 0,
    @SerialName("widewidth")
    val widewidth: Int = 0,
    @SerialName("xlarge")
    val xlarge: String? = null,
    @SerialName("xlargeheight")
    val xlargeheight: Int = 0,
    @SerialName("xlargewidth")
    val xlargewidth: Int = 0
)


fun ArticleDto.toNewsArticle(): NewsArticle {
    return NewsArticle(
        id = id,
        abstractContent = abstractContent,
        headline = headline.main,
        imageUrl = multimedia.firstOrNull()?.url?.let { url -> "https://www.nytimes.com/$url" },
        leadContent = leadParagraph,
        newsSource = source,
        url = webUrl,
        timestamp = pubDate,
        isBookmarked = false,
        articleType = ArticleType.NetworkData
    )
}

fun ArticleDto.toNewsArticleEntity(): NewsArticleEntity {
    return NewsArticleEntity(
        id = id,
        abstractContent = abstractContent,
        headline = headline.main,
        imageUrl = multimedia.firstOrNull()?.url?.let { url -> "https://www.nytimes.com/$url" },
        leadContent = leadParagraph,
        newsSource = source,
        url = webUrl,
        timestamp = pubDate,
        isBookmarked = false,
        articleType = ArticleType.NetworkData
    )
}