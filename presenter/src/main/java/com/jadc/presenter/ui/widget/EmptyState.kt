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
fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.ups),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(stringResource(R.string.product_not_found))
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyStatePreview() {
    MeliAppTheme {
        EmptyState()
    }
}