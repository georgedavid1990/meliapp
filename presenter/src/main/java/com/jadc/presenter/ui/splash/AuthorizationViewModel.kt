package com.jadc.presenter.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.mvvm.jadc.domain.util.Authorization
import com.mvvm.jadc.domain.util.RemoteConfig
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class AuthorizationViewModel(
    private val remoteConfig: FirebaseRemoteConfig,
    private val ioDispatcher: CoroutineDispatcher,
    private val mainDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading

    fun getAuthorizationConfig() {
        viewModelScope.launch(ioDispatcher) {
            _loading.update { true }
            remoteConfig
                .fetchAndActivate()
                .addOnCompleteListener {
                    Timber.i("Remote config success")
                    readConfigData()
                }
                .addOnFailureListener { exception ->
                    Timber.e(exception)
                }
        }
    }

    fun readConfigData() {
        val token = remoteConfig.getString(RemoteConfig.TOKEN)
        val refreshToken = remoteConfig.getString(RemoteConfig.REFRESH_TOKEN)
        val clientId = remoteConfig.getString(RemoteConfig.CLIENT_ID)
        val clientSecret = remoteConfig.getString(RemoteConfig.CLIENT_SECRET)
        viewModelScope.launch(mainDispatcher) {
            Authorization.token = "Bearer $token"
            Authorization.refreshToken = refreshToken
            Authorization.clientId = clientId
            Authorization.clientSecret = clientSecret
            _loading.update { false }
        }
    }
}