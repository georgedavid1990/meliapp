package com.jadc.presenter.utils

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    val connectionStatus: NetworkStatus
    val bitrate: Int

    fun observe(): Flow<NetworkStatus?>

    enum class NetworkStatus {
        Available,
        Unavailable,
        Losing,
        Lost
    }
}

open class ConnectionObserverActivity : AppCompatActivity() {
    lateinit var connectivityObserver: ConnectivityObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityObserver = NetworkConnectivityObserver(applicationContext)
    }
}