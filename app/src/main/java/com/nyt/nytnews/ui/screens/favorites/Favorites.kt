package com.nyt.nytnews.ui.screens.favorites

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nyt.nytnews.ui.navigation.NytNavigationAction
import com.nyt.nytnews.ui.screens.newsfeed.PopularNewsCard
import com.nyt.nytnews.ui.theme.HalfBaseSeparation

@Composable
fun Favorites(navigationAction: NytNavigationAction, viewModel: FavoritesViewModel) {
    val favArticles by viewModel.favArticles.collectAsState(emptyList())

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(HalfBaseSeparation),
    ) {
        items(favArticles) { article ->
            PopularNewsCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                article = article,
                navigateToArticle = { },
                onToggleBookmark = {
                    viewModel.toggleFavorite(article)
                },
                onShare = { }
            )
        }
    }
}