package com.jadc.presenter.ui.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.jadc.presenter.ui.widget.NoInternet
import com.jadc.presenter.utils.ConnectionObserverActivity
import com.jadc.presenter.utils.ConnectivityObserver
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    onLoadingFinished: () -> Unit,
    authorizationViewModel: AuthorizationViewModel = koinViewModel(),
) {
    val loading by authorizationViewModel.loading.collectAsState()
    val context = LocalContext.current
    val activity = context as ConnectionObserverActivity
    val connectionStatus = activity.connectivityObserver.observe().collectAsState(
        initial = activity.connectivityObserver.connectionStatus
    )

    LaunchedEffect(connectionStatus.value) {
        if (connectionStatus.value == ConnectivityObserver.NetworkStatus.Available) {
            authorizationViewModel.getAuthorizationConfig()
        }
    }

    LaunchedEffect(loading) {
        if (!loading) {
            onLoadingFinished()
        }
    }

    SplashStateless(connectionStatus.value)
}

@Composable
fun SplashStateless(connectionStatus: ConnectivityObserver.NetworkStatus?) {
    if (connectionStatus == ConnectivityObserver.NetworkStatus.Available) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            CircularProgressIndicator()
        }
    } else {
        NoInternet()
    }
}