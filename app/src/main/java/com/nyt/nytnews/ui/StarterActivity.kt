package com.nyt.nytnews.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.nyt.nytnews.ui.navigation.NytNavigationAction
import com.nyt.nytnews.ui.navigation.NytNavigationGraph
import com.nyt.nytnews.ui.theme.NytTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StarterActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            NytTheme {
                NytApp()
            }
        }
    }
}

@Composable
fun NytApp() {
    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        NytNavigationAction(navController)
    }
    val systemUiController = rememberSystemUiController()
    if (isSystemInDarkTheme()) {
        systemUiController.setSystemBarsColor(
            color = MaterialTheme.colors.primarySurface
        )
        systemUiController.setNavigationBarColor(
            color = MaterialTheme.colors.primarySurface
        )
    } else {
        systemUiController.setSystemBarsColor(
            color = MaterialTheme.colors.primaryVariant
        )
        systemUiController.setNavigationBarColor(
            color = MaterialTheme.colors.primaryVariant
        )
    }
    NytNavigationGraph(
        navController = navController,
        navigationAction = navigationActions
    )
}