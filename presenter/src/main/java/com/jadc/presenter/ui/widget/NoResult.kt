package com.jadc.presenter.ui.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jadc.presenter.R
import com.jadc.presenter.theme.MeliAppTheme

@Composable
fun NoResult(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.nothing_to_show_here),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(text = stringResource(R.string.please_try_searching_again))
    }
}

@Preview(showBackground = true)
@Composable
fun NoResultPreview() {
    MeliAppTheme {
        NoResult()
    }
}