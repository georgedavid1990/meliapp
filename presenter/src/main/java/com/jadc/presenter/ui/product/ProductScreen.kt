package com.jadc.presenter.ui.product

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.jadc.presenter.R
import com.jadc.presenter.ui.widget.LoadingSpinner
import com.jadc.presenter.utils.findActivity
import com.mvvm.jadc.domain.model.ProductDetailUI
import com.mvvm.jadc.domain.util.formatCurrency
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun ProductScreen(
    productId: String,
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = koinViewModel(),
    onBack: () -> Unit = {}
) {
    val loading by viewModel.loading.collectAsStateWithLifecycle()
    val productDetail by viewModel.productDetail.collectAsStateWithLifecycle()
    val activity = LocalContext.current.findActivity()

    LaunchedEffect(productId) {
        viewModel.getProductDetail(productId)
    }
    Scaffold(
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) { padding ->
        Box(
            modifier.fillMaxSize().padding(padding).background(MaterialTheme.colorScheme.surface)
        ) {
            productDetail?.let { product ->
                val windowSizeClass = activity?.let { calculateWindowSizeClass(activity) }
                ProductStateless(product, windowSizeClass, onBack)
            }

            if (loading) {
                LoadingSpinner()
            }
        }
    }
}

@Composable
fun ProductStateless(
    product: ProductDetailUI,
    windowSizeClass: WindowSizeClass?,
    onBack: () -> Unit
) {
    when (windowSizeClass?.widthSizeClass) {
        WindowWidthSizeClass.Expanded -> {
            LandscapeDetail(product, onBack)
        }

        else -> {
            PortraitDetail(product, onBack)
        }
    }
}

@Composable
fun PortraitDetail(
    product: ProductDetailUI,
    onBack: () -> Unit
) {
    Column(Modifier.testTag("portrait_detail")) {
        Back(onBack)
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(vertical = 8.dp),
            contentPadding = PaddingValues(
                horizontal = 16.dp
            ),
            horizontalArrangement = Arrangement.Center
        ) {
            items(product.pictures.size) {
                val uri = product.pictures[it]
                AsyncImage(
                    model = Uri.parse(uri),
                    contentDescription = product.name,
                    modifier = Modifier
                        .size(300.dp)
                        .background(Color.White)
                        .clip(shape = RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Fit,
                    placeholder = painterResource(R.drawable.placeholder),
                    error = painterResource(R.drawable.placeholder)
                )
                Spacer(Modifier.width(12.dp))
            }
        }
        ProductInfo(product)
    }
}

@Composable
fun LandscapeDetail(
    product: ProductDetailUI,
    onBack: () -> Unit
) {
    Row(Modifier.testTag("landscape_detail")) {
        Column(modifier = Modifier.weight(5.5f)) {
            Back(onBack)
            ProductInfo(product)
        }
        Spacer(Modifier.width(16.dp))
        LazyRow(
            modifier = Modifier
                .weight(4.5f)
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.primary)
                .padding(vertical = 8.dp),
            contentPadding = PaddingValues(
                horizontal = 16.dp
            ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(product.pictures.size) {
                val uri = product.pictures[it]
                AsyncImage(
                    model = Uri.parse(uri),
                    contentDescription = product.name,
                    modifier = Modifier
                        .size(300.dp)
                        .background(Color.White)
                        .clip(shape = RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Fit,
                    placeholder = painterResource(R.drawable.placeholder),
                    error = painterResource(R.drawable.placeholder)
                )
                Spacer(Modifier.width(12.dp))
            }
        }
    }
}

@Composable
fun Back(onBack: () -> Unit) {
    IconButton(
        onClick = onBack
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun ProductInfo(product: ProductDetailUI) {
    Column(Modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = product.name,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = product.price.formatCurrency(Locale.US),
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Disponibles: ${product.stock}",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Características",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(8.dp))
        product.attributes.forEach { attr ->
            Text(
                text = attr,
                style = MaterialTheme.typography.bodySmall
            )
            HorizontalDivider(Modifier.fillMaxWidth().padding(8.dp))
        }
    }
}