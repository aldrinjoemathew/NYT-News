package com.nyt.nytnews.ui.screens.newsfeed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nyt.nytnews.R
import com.nyt.nytnews.domain.models.NewsArticle
import com.nyt.nytnews.ui.theme.BaseSeparation
import com.nyt.nytnews.ui.theme.HalfBaseSeparation

@Composable
fun SimpleNewsCard(
    article: NewsArticle,
    navigateToArticle: (String) -> Unit,
    onToggleBookmark: () -> Unit,
    onShare: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { },
    ) {
        Row {
            if (!article.imageUrl.isNullOrEmpty()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(article.imageUrl)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.ic_launcher_foreground),
                    error = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = article.headline,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(150.dp, 180.dp)
                )
            }
            Column(
                modifier = Modifier
                    .heightIn(max = 180.dp, min = 0.dp)
                    .padding(
                        start = BaseSeparation,
                        end = BaseSeparation,
                        top = BaseSeparation,
                        bottom = 0.dp
                    ),
            ) {
                Text(
                    text = article.headline,
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    fontWeight = FontWeight.Bold,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(BaseSeparation))
                Text(
                    text = article.leadContent,
                    fontSize = MaterialTheme.typography.body2.fontSize,
                    fontWeight = FontWeight.Normal,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f, fill = false)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.End
                ) {
                    BookmarkButton(isBookmarked = article.isBookmarked, onToggle = { onToggleBookmark() })
                    ShareButton(onShare = { onShare() })
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(HalfBaseSeparation))
}