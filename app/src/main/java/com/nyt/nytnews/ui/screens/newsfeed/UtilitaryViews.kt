package com.nyt.nytnews.ui.screens.newsfeed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nyt.nytnews.R
import com.nyt.nytnews.ui.icons.Bookmark
import com.nyt.nytnews.ui.icons.BookmarkBorder
import com.nyt.nytnews.ui.theme.BaseSeparation

@Composable
fun LoadingItem(fullPage: Boolean) {
    Box(modifier = Modifier
        .run {
            if (fullPage) fillMaxSize() else fillMaxWidth()
        }
        .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorRetry(
    onRetry: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(BaseSeparation)
            .clickable {
                onRetry()
            },
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(R.string.action_retry))
        Spacer(modifier = Modifier.size(BaseSeparation))
        Icon(
            imageVector = Icons.Filled.Refresh,
            contentDescription = stringResource(R.string.refresh)
        )
    }
}

@Composable
fun ShareButton(onShare: () -> Unit) {
    IconButton(onClick = onShare) {
        Icon(
            imageVector = Icons.Filled.Share,
            contentDescription = stringResource(R.string.share_article)
        )
    }
}

@Composable
fun BookmarkButton(isBookmarked: Boolean, onToggle: () -> Unit) {
    IconToggleButton(
        checked = isBookmarked,
        onCheckedChange = { onToggle() },
    ) {
        Icon(
            imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
            contentDescription = null // handled by click label of parent
        )
    }
}