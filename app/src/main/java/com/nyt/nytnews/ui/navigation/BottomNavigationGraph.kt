package com.nyt.nytnews.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nyt.nytnews.ui.screens.favorites.Favorites
import com.nyt.nytnews.ui.screens.newsfeed.NewsFeed
import com.nyt.nytnews.ui.screens.profile.Profile

@Composable
fun BottomNavigationGraph(
    navController: NavHostController,
    navigationAction: NytNavigationAction,
) {
    NavHost(navController = navController, startDestination = NavRoutes.NEWS_FEED) {
        composable(NavRoutes.NEWS_FEED) {
            NewsFeed(navigationAction = navigationAction, viewModel = hiltViewModel())
        }
        composable(NavRoutes.FAVORITES) {
            Favorites(navigationAction = navigationAction, viewModel = hiltViewModel())
        }
        composable(NavRoutes.PROFILE) {
            Profile(navigationAction = navigationAction, viewModel = hiltViewModel())
        }
    }
}