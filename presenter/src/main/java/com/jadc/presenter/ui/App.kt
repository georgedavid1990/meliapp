package com.jadc.presenter.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.jadc.presenter.ui.graph.ProductFlow
import com.jadc.presenter.ui.splash.SplashScreen

@Composable
fun App() {
    var loading by rememberSaveable { mutableStateOf(true) }

    if (loading) {
        SplashScreen(
            onLoadingFinished = {
                loading = false
            }
        )
    } else {
        ProductFlow()
    }
}