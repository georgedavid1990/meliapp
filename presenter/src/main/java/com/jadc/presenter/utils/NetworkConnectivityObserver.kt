package com.jadc.presenter.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.util.Log
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class NetworkConnectivityObserver(
    context: Context
) : ConnectivityObserver {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun observe(): Flow<ConnectivityObserver.NetworkStatus> =
        callbackFlow {
            val networkCallback =
                object : ConnectivityManager.NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        super.onAvailable(network)
                        launch { send(ConnectivityObserver.NetworkStatus.Available) }
                    }

                    override fun onLosing(
                        network: Network,
                        maxMsToLive: Int
                    ) {
                        super.onLosing(network, maxMsToLive)
                        launch { send(ConnectivityObserver.NetworkStatus.Losing) }
                    }

                    override fun onLost(network: Network) {
                        super.onLost(network)
                        launch { send(ConnectivityObserver.NetworkStatus.Lost) }
                    }

                    override fun onUnavailable() {
                        super.onUnavailable()
                        launch { send(ConnectivityObserver.NetworkStatus.Unavailable) }
                    }
                }
            try {
                connectivityManager.registerDefaultNetworkCallback(networkCallback)
            } catch (e: RuntimeException) {
                Log.e(TAG, e.toString())
            }
            awaitClose {
                connectivityManager.unregisterNetworkCallback(networkCallback)
            }
        }.distinctUntilChanged()

    override val connectionStatus: ConnectivityObserver.NetworkStatus
        get() {
            val connected =
                connectivityManager.allNetworks.any { net ->
                    connectivityManager
                        .getNetworkCapabilities(net)
                        ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                        ?: false
                }

            return if (connected) {
                ConnectivityObserver.NetworkStatus.Available
            } else {
                ConnectivityObserver.NetworkStatus.Unavailable
            }
        }

    override val bitrate: Int
        get() {
            val nc = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            return (nc?.linkDownstreamBandwidthKbps ?: 0) * 1024
        }

    companion object {
        private const val TAG = "ConnectivityObserver"
    }
}