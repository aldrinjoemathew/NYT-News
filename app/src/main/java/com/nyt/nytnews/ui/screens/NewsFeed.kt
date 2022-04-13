package com.nyt.nytnews.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
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
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.nyt.nytnews.R
import com.nyt.nytnews.models.NewsArticle
import com.nyt.nytnews.ui.composables.ChipGroup
import com.nyt.nytnews.ui.icons.Bookmark
import com.nyt.nytnews.ui.icons.BookmarkBorder
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
    val popularArticles by viewModel.popularArticles.collectAsState()

    viewModel.updateRefreshing(newsArticles.loadState.refresh is LoadState.Loading)

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
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = newsArticles.loadState.refresh == LoadState.Loading),
                onRefresh = {
                    newsArticles.refresh()
                }) {
                ArticleFeed(padding, popularArticles, newsArticles)
            }
        }
    }
}

@Composable
private fun ArticleFeed(
    padding: PaddingValues,
    popularArticles: List<NewsArticle>,
    newsArticles: LazyPagingItems<NewsArticle>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(HalfBaseSeparation),
    ) {
        if (popularArticles.isNotEmpty()) {
            item {
                PopularNewsFeed(popularArticles)
            }
        }
        items(newsArticles.itemCount) { index ->
            newsArticles[index]?.let { article ->
                //Randomize and show popular cards in between
                if (index % 3 == 0 && article.headline.length % 2 == 0 && !article.imageUrl.isNullOrEmpty())
                    PopularNewsCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        article = article,
                        navigateToArticle = { },
                        onToggleBookmark = { },
                        onShare = { }
                    )
                else NewsCard(
                    article = article,
                    navigateToArticle = { },
                    onToggleBookmark = { },
                    onShare = { }
                )
            }
        }
        newsArticles.apply {
            when {
                loadState.append is LoadState.Loading -> {
                    item { LoadingItem(fullPage = false) }
                }
                loadState.refresh is LoadState.Error -> {
                    //Move this outside of LazyColumn to show a full screen error view
                    item { ErrorRetry(onRetry = { retry() }) }
                }
                loadState.append is LoadState.Error -> {
                    item { ErrorRetry(onRetry = { retry() }) }
                }
            }
        }
    }
}

@Composable
private fun PopularNewsFeed(popularArticles: List<NewsArticle>) {
    Column {
        Spacer(modifier = Modifier.height(BaseSeparation))
        Text(text = "Popular stories", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(BaseSeparation))
        LazyRow(modifier = Modifier) {
            itemsIndexed(popularArticles) { index, article ->
                PopularNewsCard(
                    modifier = Modifier
                        .padding(
                            start = if (index == 0) 0.dp else HalfBaseSeparation,
                            bottom = HalfBaseSeparation,
                        )
                        .width(280.dp),
                    article = article,
                    navigateToArticle = { },
                    onToggleBookmark = { },
                    onShare = { }
                )
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
    ChipGroup(
        modifier = Modifier
            .heightIn(min = 0.dp, max = 300.dp)
            .fillMaxWidth()
            .padding(BaseSeparation),
        chipItems = filters,
        onSelectedChanged = {
            onFilterChanged(it)
        },
    )
}

@Composable
private fun NewsCard(
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
                    BookmarkButton(isBookmarked = false, onToggle = { onToggleBookmark() })
                    ShareButton(onShare = { onShare() })
                }
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

            Column(modifier = Modifier.padding(
                start = BaseSeparation,
                end = BaseSeparation,
                top = BaseSeparation,
                bottom = 0.dp
            )) {
                Text(
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
                    BookmarkButton(isBookmarked = false, onToggle = { onToggleBookmark() })
                    ShareButton(onShare = { onShare() })
                }
            }
        }
    }
}

@Composable
private fun ShareButton(onShare: () -> Unit) {
    IconButton(onClick = onShare) {
        Icon(
            imageVector = Icons.Filled.Share,
            contentDescription = "Share article"
        )
    }
}

@Composable
private fun BookmarkButton(isBookmarked: Boolean, onToggle: () -> Unit) {
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