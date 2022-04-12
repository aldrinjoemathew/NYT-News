package com.nyt.nytnews.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nyt.nytnews.ui.navigation.NytNavigationAction
import com.nyt.nytnews.ui.theme.BaseSeparation
import com.nyt.nytnews.ui.theme.HalfBaseSeparation
import com.nyt.nytnews.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(navigationAction: NytNavigationAction, viewModel: HomeViewModel) {
    val newsArticles by viewModel.newsArticles.collectAsState()
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(BaseSeparation),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(newsArticles) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .align(Alignment.Center)
                        .padding(HalfBaseSeparation),
                    backgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.5f),
                    elevation = 0.dp
                ) {
                    Text(text = item.headline, modifier = Modifier.padding(BaseSeparation))
                }
            }
        }
    }
}