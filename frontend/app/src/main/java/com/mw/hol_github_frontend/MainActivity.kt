package com.mw.hol_github_frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mw.hol_github_frontend.composable.Navigation
import com.mw.hol_github_frontend.composable.Providers
import com.mw.hol_github_frontend.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                Providers {
                    Navigation()
                }
            }
        }
    }
}