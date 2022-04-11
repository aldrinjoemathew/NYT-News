package com.nyt.nytnews.ui.navigation

import androidx.navigation.NavController

class NytNavigationAction(navController: NavController) {
    val navigateToHome: () -> Unit = {

    }
    val navigateToSignup: () -> Unit = {
        navController.navigate(NavRoutes.SIGNUP)
    }
}