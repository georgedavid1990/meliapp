package com.jadc.presenter.ui.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jadc.presenter.ui.product.ProductScreen
import com.jadc.presenter.ui.search.SearchScreen
import com.jadc.presenter.ui.widget.EmptyState

@Composable
fun ProductFlow() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ProductNavGraph.Search.route
    ) {
        productNavGraphRoutes.forEach { destination ->
            when (destination) {
                ProductNavGraph.Search -> composable(
                    route = destination.route
                ) {
                    SearchScreen(
                        onClick = { product ->
                            navController.navigate("${ProductNavGraph.Detail.route}/${product.id}")
                        }
                    )
                }
                ProductNavGraph.Detail -> composable(
                    route = "${destination.route}/{id}",
                    arguments = listOf(navArgument("id") { type = NavType.StringType })
                ) { navBackStackEntry ->
                    val productId = navBackStackEntry.arguments?.getString("id")
                    if (productId != null) {
                        ProductScreen(
                            productId,
                            onBack = {
                                navController.popBackStack()
                            }
                        )
                    } else {
                        EmptyState()
                    }
                }
            }
        }
    }
}