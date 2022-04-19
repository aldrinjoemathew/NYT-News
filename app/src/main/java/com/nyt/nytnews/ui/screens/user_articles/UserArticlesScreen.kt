package com.nyt.nytnews.ui.screens.user_articles

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.nyt.nytnews.R
import com.nyt.nytnews.ui.navigation.NytNavigationAction
import com.nyt.nytnews.ui.theme.HalfBaseSeparation

@Composable
fun UserArticlesScreen(navigationAction: NytNavigationAction, viewModel: UserArticlesViewModel) {
    val userArticles by viewModel.userArticles.collectAsState(emptyList())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navigationAction.navigateCreateUserArticleScreen()
            }) {

                Icon(
                    imageVector = Icons.Outlined.Add,
                    tint = MaterialTheme.colors.onSecondary,
                    contentDescription = stringResource(R.string.filter)
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues = padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(HalfBaseSeparation),
        ) {
            items(userArticles) { article ->
                UserArticleCard(
                    article = article,
                    navigateToArticle = { },
                    onDelete = {
                        viewModel.deleteArticle(article)
                    },
                    onShare = { }
                )
            }
        }
    }
}