package com.nyt.nytnews.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nyt.nytnews.R
import com.nyt.nytnews.ui.navigation.BottomNavigationGraph
import com.nyt.nytnews.ui.navigation.NavRoutes
import com.nyt.nytnews.ui.navigation.NytNavigationAction

@Composable
fun HomeScreen(
    navController: NavHostController,
    navigationAction: NytNavigationAction,
) {

    val bottomNavController = rememberNavController()
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colors.surface,
            ),
            title = {
                Image(
                    modifier = Modifier.wrapContentSize(),
                    painter = painterResource(id = R.drawable.ic_newyorktimes),
                    contentDescription = "New york times",
                    alignment = Alignment.Center,
                    colorFilter = ColorFilter.tint(contentColorFor(backgroundColor = MaterialTheme.colors.surface))
                )
            })
    }, bottomBar = {
        BottomNavigationBar(navController = bottomNavController)
    }) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            BottomNavigationGraph(
                navController = bottomNavController,
                navigationAction = navigationAction
            )
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val bottomNavItems =
        listOf(
            NavigationItem.NewsFeed,
            NavigationItem.Favorites,
            NavigationItem.NewArticle,
            NavigationItem.Profile
        )
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = contentColorFor(backgroundColor = MaterialTheme.colors.surface)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        bottomNavItems.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

sealed class NavigationItem(var route: String, var icon: ImageVector, var title: String) {
    object NewsFeed : NavigationItem(NavRoutes.NEWS_FEED, Icons.Rounded.Home, "Home")
    object Favorites : NavigationItem(NavRoutes.FAVORITES, Icons.Rounded.Favorite, "Favorites")
    object Profile : NavigationItem(NavRoutes.PROFILE, Icons.Rounded.Person, "Profile")
    object NewArticle :
        NavigationItem(NavRoutes.USER_ARTICLES, Icons.Rounded.Star, "User articles")
}

