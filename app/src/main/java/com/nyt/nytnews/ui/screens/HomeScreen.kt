package com.nyt.nytnews.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nyt.nytnews.R
import com.nyt.nytnews.ui.navigation.NytNavigationAction
import com.nyt.nytnews.ui.theme.BaseSeparation
import com.nyt.nytnews.ui.theme.HalfBaseSeparation
import com.nyt.nytnews.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(navigationAction: NytNavigationAction, viewModel: HomeViewModel) {
    val newsArticles = viewModel.newsArticles.collectAsLazyPagingItems()
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(HalfBaseSeparation),
        ) {

            items(newsArticles.itemCount) { index ->
                newsArticles[index]?.let { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .align(Alignment.Center),
                        backgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.5f),
                        elevation = 0.dp
                    ) {
                        Row {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(item.imageUrl)
                                    .crossfade(true)
                                    .build(),
                                placeholder = painterResource(R.drawable.ic_launcher_foreground),
                                contentDescription = item.headline,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(150.dp)
                            )
                            Column (modifier = Modifier
                                .height(150.dp)
                                .padding(BaseSeparation)) {
                                Text(
                                    text = item.headline,
                                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 3,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(BaseSeparation))
                                Text(
                                    text = item.leadContent,
                                    fontSize = MaterialTheme.typography.body2.fontSize,
                                    fontWeight = FontWeight.Normal,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.weight(1f)
                                )
                                Spacer(modifier = Modifier.height(BaseSeparation))
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(HalfBaseSeparation))
                }
            }
            newsArticles.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item { LoadingItem(fullPage = true) }
                    }
                    loadState.append is LoadState.Loading -> {
                        item { LoadingItem(fullPage = false) }
                    }
                    loadState.refresh is LoadState.Error -> {

                    }
                    loadState.append is LoadState.Error -> {
                        item {
                            ErrorRetry(onRetry = {
                                retry()
                            })
                        }
                    }
                }
            }
        }
    }
}

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
        Text(text = "Retry")
        Spacer(modifier = Modifier.size(BaseSeparation))
        Icon(imageVector = Icons.Filled.Refresh, contentDescription = "Refresh")
    }
}