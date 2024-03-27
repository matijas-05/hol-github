package com.mw.hol_github_frontend.composable

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.mw.hol_github_frontend.api.ApiClient
import com.mw.hol_github_frontend.screen.auth.signin.SignInScreen
import com.mw.hol_github_frontend.screen.auth.signup.SignUpScreen
import com.mw.hol_github_frontend.screen.main.UserScreen

const val DURATION = 300

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val apiClient = ApiClient(LocalContext.current.applicationContext as Application)

    fun afterAuth() {
        val mainHandler = Handler(Looper.getMainLooper())
        val runnable = Runnable {
            navController.popBackStack("auth", inclusive = true)
            navController.navigate("main")
        }
        mainHandler.post(runnable)
    }

    NavHost(
        navController = navController,
        startDestination = "auth",
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                tween(DURATION)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                tween(DURATION)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                tween(DURATION)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                tween(DURATION)
            )
        },
    ) {
        navigation(route = "auth", startDestination = "signin") {
            composable("signup") {
                SignUpScreen(apiClient = apiClient,
                    navigateToSignIn = { navController.navigate("signin") },
                    onSignUp = { afterAuth() }
                )
            }
            composable("signin") {
                SignInScreen(
                    apiClient = apiClient,
                    navigateToSignUp = { navController.navigate("signup") },
                    onSignIn = { afterAuth() }
                )
            }
        }

        navigation(route = "main", startDestination = "user") {
            composable("user") {
                UserScreen()
            }
        }
    }
}