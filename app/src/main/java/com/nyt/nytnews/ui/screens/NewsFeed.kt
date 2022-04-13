package com.nyt.nytnews.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.nyt.nytnews.R
import com.nyt.nytnews.models.NewsArticle
import com.nyt.nytnews.ui.composables.ChipGroup
import com.nyt.nytnews.ui.navigation.NytNavigationAction
import com.nyt.nytnews.ui.theme.BaseSeparation
import com.nyt.nytnews.ui.theme.HalfBaseSeparation
import com.nyt.nytnews.ui.viewmodel.NewsFeedViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsFeed(navigationAction: NytNavigationAction, viewModel: NewsFeedViewModel) {
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    val newsArticles = viewModel.newsArticles.collectAsLazyPagingItems()

    ModalBottomSheetLayout(
        sheetContent = {
            BottomSheetContent(onFilterChanged = {
                viewModel.updateFilter(it)
                scope.launch {
                    modalBottomSheetState.hide()
                }
            })
        },
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = HalfBaseSeparation, topEnd = HalfBaseSeparation),
        sheetBackgroundColor = MaterialTheme.colors.background,
    ) {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    scope.launch {
                        if (modalBottomSheetState.isVisible) {
                            modalBottomSheetState.hide()
                        } else {
                            modalBottomSheetState.show()
                        }
                    }
                }) {
                    Icon(
                        painter = rememberAsyncImagePainter(model = R.drawable.ic_baseline_filter_alt_24),
                        tint = MaterialTheme.colors.onSecondary,
                        contentDescription = "Filter"
                    )
                }
            },
        ) { padding ->
            SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = newsArticles.loadState.refresh == LoadState.Loading), onRefresh = {
                newsArticles.refresh()
            }) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(HalfBaseSeparation),
                ) {
                    items(newsArticles.itemCount) { index ->
                        newsArticles[index]?.let { item ->
                            NewsCard(item)
                        }
                    }
                    newsArticles.apply {
                        when {
                            /*loadState.refresh is LoadState.Loading -> {
                                item { LoadingItem(fullPage = true) }
                            }*/ //Already taken care by swipe to refresh
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
    }
}

val filters = listOf(
    "Arts",
    "Automobiles",
    "Autos",
    "Blogs",
    "Books",
    "Booming",
    "Business",
    "Business Day",
    "Corrections",
    "Crosswords & Games",
    "Crosswords/Games",
    "Dining & Wine",
    "Dining and Wine",
    "Editors' Notes",
    "Education",
    "Fashion & Style",
    "Food",
    "Front Page",
    "Giving",
    "Global Home",
    "Great Homes & Destinations",
    "Great Homes and Destinations",
    "Health",
    "Home & Garden",
    "Home and Garden",
    "International Home",
    "Job Market",
    "Learning",
    "Magazine",
    "Movies",
    "Multimedia",
    "Multimedia/Photos",
    "N.Y. / Region",
    "N.Y./Region",
    "NYRegion",
    "NYT Now",
    "National",
    "New York",
    "New York and Region",
    "Obituaries",
    "Olympics",
    "Open",
    "Opinion",
    "Paid Death Notices",
    "Public Editor",
    "Real Estate",
    "Science",
    "Sports",
    "Style",
    "Sunday Magazine",
    "Sunday Review",
    "T Magazine",
    "T:Style",
    "Technology",
    "The Public Editor",
    "The Upshot",
    "Theater",
    "Times Topics",
    "TimesMachine",
    "Today's Headlines",
    "Topics",
    "Travel",
    "U.S.",
    "Universal",
    "UrbanEye",
    "Washington",
    "Week in Review",
    "World"
)

@Composable
fun BottomSheetContent(onFilterChanged: (String) -> Unit) {
    ChipGroup(chipItems = filters, onSelectedChanged = {
        onFilterChanged(it)
    })
}

@Composable
private fun NewsCard(item: NewsArticle) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        backgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.5f),
        elevation = 0.dp
    ) {
        Row {
            if (!item.imageUrl.isNullOrEmpty()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(item.imageUrl)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.ic_launcher_foreground),
                    error = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = item.headline,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(150.dp)
                )
            }
            Column(
                modifier = Modifier
                    .heightIn(max = 150.dp, min = 0.dp)
                    .padding(horizontal = BaseSeparation, vertical = BaseSeparation),
            ) {
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
                    modifier = Modifier.weight(1f, fill = false)
                )
                Spacer(modifier = Modifier.height(BaseSeparation))
            }
        }
    }
    Spacer(modifier = Modifier.height(HalfBaseSeparation))
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