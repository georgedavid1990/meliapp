package com.jadc.presenter.ui.widget

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jadc.presenter.R
import com.jadc.presenter.theme.MeliAppTheme
import com.mvvm.jadc.domain.model.ProductUI
import com.mvvm.jadc.domain.util.formatCurrency
import java.util.Locale

@Composable
fun ProductCard(
    productUI: ProductUI,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCardClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = Uri.parse(productUI.thumbnailUrl),
                contentDescription = productUI.name,
                modifier = Modifier
                    .size(150.dp)
                    .clip(shape = RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Fit,
                placeholder = painterResource(R.drawable.placeholder),
                error = painterResource(R.drawable.placeholder)
            )
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = productUI.name,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = productUI.price.formatCurrency(Locale.US),
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.disponibles, productUI.stock),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview
@Composable
fun ProductCardPreview() {
    MeliAppTheme {
        ProductCard(
            productUI = ProductUI(
                id = "",
                name = "Test product with a very long name that needs to wrap",
                thumbnailUrl = "",
                price = 1000000.0,
                stock = 100
            ),
            onCardClick = {},
            modifier = Modifier.height(150.dp)
        )
    }
}