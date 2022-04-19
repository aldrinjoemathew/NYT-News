package com.nyt.nytnews.ui.navigation

import androidx.navigation.NavController

class NytNavigationAction(navController: NavController) {
    val navigateToHome: () -> Unit = {
        navController.navigate(NavRoutes.HOME) {
            popUpTo(NavRoutes.LOGIN) {
                inclusive = true
            }
        }
    }
    val navigateToSignup: () -> Unit = {
        navController.navigate(NavRoutes.SIGNUP)
    }
    val navigateCreateUserArticleScreen: () -> Unit = {
        navController.navigate(NavRoutes.NEW_ARTICLE)
    }
    val popBackstack: () -> Unit = {
        navController.popBackStack()
    }
}