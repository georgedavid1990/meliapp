package com.jadc.presenter.ui.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jadc.presenter.R
import com.jadc.presenter.theme.MeliAppTheme

@Composable
fun SearchBar(
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {}
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.search_items)
            )
        },
        colors =
            TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedContainerColor = MaterialTheme.colorScheme.surface
            ),
        placeholder = {
            Text(
                text = stringResource(R.string.search_items),
                style = MaterialTheme.typography.labelMedium.copy(color = Color.Gray)
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
    )
}

@Preview
@Composable
fun SearchBarPreview() {
    MeliAppTheme {
        SearchBar(value = "")
    }
}