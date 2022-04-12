package com.nyt.nytnews.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nyt.nytnews.ui.screens.HomeScreen
import com.nyt.nytnews.ui.screens.LoginScreen
import com.nyt.nytnews.ui.screens.SignupScreen

@Composable
fun NytNavigationGraph(
    navController: NavHostController,
    navigationAction: NytNavigationAction,
) {
    NavHost(navController = navController, startDestination = NavRoutes.HOME) {
        composable(NavRoutes.LOGIN) {
            LoginScreen(navigationAction = navigationAction, viewModel = hiltViewModel())
        }
        composable(NavRoutes.SIGNUP) {
            SignupScreen(navigationAction = navigationAction, viewModel = hiltViewModel())
        }
        composable(NavRoutes.HOME) {
            HomeScreen(navigationAction = navigationAction, viewModel = hiltViewModel())
        }
    }
}