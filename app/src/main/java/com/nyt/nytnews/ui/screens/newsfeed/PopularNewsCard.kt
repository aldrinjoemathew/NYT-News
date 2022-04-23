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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nyt.nytnews.R
import com.nyt.nytnews.domain.models.NewsArticle
import com.nyt.nytnews.ui.theme.BaseSeparation
import com.nyt.nytnews.ui.theme.HalfBaseSeparation

@Composable
fun PopularNewsCard(
    modifier: Modifier = Modifier,
    article: NewsArticle,
    navigateToArticle: (String) -> Unit,
    onToggleBookmark: () -> Unit,
    onShare: () -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier,
    ) {
        Column(modifier = Modifier.clickable(onClick = { })) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(article.imageUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_launcher_foreground),
                error = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = article.headline,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )

            Column(
                modifier = Modifier.padding(
                    start = BaseSeparation,
                    end = BaseSeparation,
                    top = BaseSeparation,
                    bottom = 0.dp
                )
            ) {
                val titleLineHeight = MaterialTheme.typography.h6.fontSize * 4 / 3
                Text(
                    modifier = Modifier.height(with(LocalDensity.current) {
                        (titleLineHeight * 2).toDp()
                    }),
                    text = article.headline,
                    style = MaterialTheme.typography.h6,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = article.abstractContent,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.body2
                )
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    BookmarkButton(isBookmarked = article.isBookmarked, onToggle = { onToggleBookmark() })
                    ShareButton(onShare = { onShare() })
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(HalfBaseSeparation))
}