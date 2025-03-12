package com.jadc.presenter.ui.splash

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.common.truth.Truth.assertThat
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.mvvm.jadc.domain.util.Authorization
import com.mvvm.jadc.domain.util.RemoteConfig
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Test

class AuthorizationViewModelTest {

    private lateinit var sut: AuthorizationViewModel

    private lateinit var remoteConfig: FirebaseRemoteConfig

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        remoteConfig = mockk(relaxed = true)
        sut = AuthorizationViewModel(
            remoteConfig,
            testDispatcher,
            testDispatcher
        )
    }

    @Test
    fun read_remote_config_updates_authorization_data() {
        coEvery { remoteConfig.getString(RemoteConfig.TOKEN) } returns "A"
        coEvery { remoteConfig.getString(RemoteConfig.REFRESH_TOKEN) } returns "B"
        coEvery { remoteConfig.getString(RemoteConfig.CLIENT_ID) } returns "C"
        coEvery { remoteConfig.getString(RemoteConfig.CLIENT_SECRET) } returns "D"

        val loadingInit = sut.loading.value
        sut.readConfigData()
        val loadingEnd = sut.loading.value

        assertThat(Authorization.token).isEqualTo("Bearer A")
        assertThat(Authorization.refreshToken).isEqualTo("B")
        assertThat(Authorization.clientId).isEqualTo("C")
        assertThat(Authorization.clientSecret).isEqualTo("D")
        assertThat(loadingInit).isTrue()
        assertThat(loadingEnd).isFalse()
    }
}