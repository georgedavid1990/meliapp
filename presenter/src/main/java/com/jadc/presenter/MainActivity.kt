package com.jadc.presenter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jadc.presenter.theme.MeliAppTheme
import com.jadc.presenter.ui.App
import com.jadc.presenter.utils.ConnectionObserverActivity

class MainActivity : ConnectionObserverActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeliAppTheme {
                App()
            }
        }
    }
}