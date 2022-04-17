package com.nyt.nytnews.ui.screens.newsfeed

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.nyt.nytnews.R
import com.nyt.nytnews.domain.models.NewsArticle
import com.nyt.nytnews.ui.composables.ChipGroup
import com.nyt.nytnews.ui.navigation.NytNavigationAction
import com.nyt.nytnews.ui.theme.BaseSeparation
import com.nyt.nytnews.ui.theme.HalfBaseSeparation
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsFeed(navigationAction: NytNavigationAction, viewModel: NewsFeedViewModel) {
    val modalBottomSheetState =
        rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            skipHalfExpanded = true
        )
    val scope = rememberCoroutineScope()
    val newsArticles = viewModel.newsArticles.collectAsLazyPagingItems()
    val popularArticles by viewModel.popularArticles.collectAsState()
    val filter by viewModel.filter.collectAsState()

    if (newsArticles.itemCount == 0 && newsArticles.loadState.refresh is LoadState.NotLoading) return

    viewModel.updateRefreshing(newsArticles.loadState.refresh is LoadState.Loading)

    BackHandler(enabled = modalBottomSheetState.isVisible) {
        scope.launch {
            modalBottomSheetState.hide()
        }
    }

    ModalBottomSheetLayout(
        sheetContent = {
            BottomSheetContent(
                filterItems = stringArrayResource(id = R.array.news_filters).toList(),
                selectedFilter = filter,
                onFilterChanged = {
                    viewModel.updateFilter(it)
                    scope.launch {
                        modalBottomSheetState.hide()
                    }
                }, closeFilter = {
                    scope.launch {
                        modalBottomSheetState.hide()
                    }
                })
        },
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(0),
        sheetBackgroundColor = MaterialTheme.colors.background,
    ) {
        Scaffold { padding ->
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = newsArticles.loadState.refresh == LoadState.Loading),
                onRefresh = {
                    newsArticles.refresh()
                }) {
                ArticleFeed(
                    padding,
                    popularArticles,
                    newsArticles,
                    filterItems = stringArrayResource(id = R.array.news_filters).toList(),
                    selectedFilter = filter,
                    showFilter = {
                        scope.launch {
                            if (modalBottomSheetState.isVisible) {
                                modalBottomSheetState.hide()
                            } else {
                                modalBottomSheetState.show()
                            }
                        }
                    }, onFilterChanged = {
                        viewModel.updateFilter(it)
                    }
                )
            }
        }
    }
}

@Composable
private fun ArticleFeed(
    padding: PaddingValues,
    popularArticles: List<NewsArticle>,
    newsArticles: LazyPagingItems<NewsArticle>,
    filterItems: List<String>,
    selectedFilter: String,
    onFilterChanged: (String) -> Unit,
    showFilter: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(HalfBaseSeparation),
    ) {
        item {
            LazyRow(
                verticalAlignment = Alignment.CenterVertically
            ) {
                item {
                    Surface(
                        modifier = Modifier
                            .clickable { showFilter() }
                            .padding(horizontal = HalfBaseSeparation),
                        elevation = BaseSeparation,
                        shape = CircleShape,
                        color = MaterialTheme.colors.surface
                    ) {
                        Icon(
                            modifier = Modifier.padding(HalfBaseSeparation),
                            painter = painterResource(R.drawable.ic_baseline_filter_alt_24),
                            contentDescription = stringResource(R.string.filter),
                            tint = MaterialTheme.colors.onSurface
                        )
                    }
                }
                items(filterItems) { item ->
                    com.nyt.nytnews.ui.composables.Chip(
                        name = item,
                        isSelected = selectedFilter == item,
                        onSelectionChanged = {
                            onFilterChanged(it)
                        },
                    )
                }
            }
        }
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
                else
                    SimpleNewsCard(
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
        Text(text = stringResource(R.string.popular_stories), style = MaterialTheme.typography.h5)
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

@Composable
fun BottomSheetContent(
    onFilterChanged: (String) -> Unit,
    closeFilter: () -> Unit,
    filterItems: List<String>,
    selectedFilter: String
) {
    Column {
        Row(
            modifier = Modifier.padding(BaseSeparation),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.choose_filter),
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold
            )
            OutlinedButton(
                modifier= Modifier.size(40.dp),
                onClick = closeFilter,
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp),
            ) {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = stringResource(R.string.cd_close_filter)
                )
            }
            Spacer(modifier = Modifier.width(BaseSeparation))
        }
        ChipGroup(
            modifier = Modifier
                .padding(BaseSeparation),
            chipItems = filterItems,
            onSelectedChanged = {
                onFilterChanged(it)
            },
            selectedItem = selectedFilter
        )
    }
}

