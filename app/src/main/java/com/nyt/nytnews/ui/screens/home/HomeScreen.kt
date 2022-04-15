package com.nyt.nytnews.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
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
        TopAppBar(title = {
            Text(text = stringResource(R.string.app_name))
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
        listOf(NavigationItem.NewsFeed, NavigationItem.Favorites, NavigationItem.Profile)
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primarySurface,
        contentColor = contentColorFor(backgroundColor = MaterialTheme.colors.primarySurface)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        bottomNavItems.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = { Text(text = item.title) },
                selectedContentColor = contentColorFor(
                    backgroundColor = MaterialTheme.colors.primarySurface
                ),
                unselectedContentColor = contentColorFor(
                    backgroundColor = MaterialTheme.colors.primarySurface
                ).copy(0.6f),
                alwaysShowLabel = true,
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
    object NewsFeed : NavigationItem(NavRoutes.NEWS_FEED, Icons.Outlined.Home, "Home")
    object Favorites : NavigationItem(NavRoutes.FAVORITES, Icons.Outlined.Favorite, "Favorites")
    object Profile : NavigationItem(NavRoutes.PROFILE, Icons.Outlined.Person, "Profile")
}
