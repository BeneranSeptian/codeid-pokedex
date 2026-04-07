package dev.septianbeneran.technicaltest.feature.a.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import dev.septianbeneran.technicaltest.core.base.BaseNavGraph
import dev.septianbeneran.technicaltest.core.entity.model.Item
import dev.septianbeneran.technicaltest.core.navigation.route.ItemDetailRoute
import dev.septianbeneran.technicaltest.core.navigation.route.ItemListRoute
import dev.septianbeneran.technicaltest.core.util.navtype.typeMapOf
import dev.septianbeneran.technicaltest.feature.a.screen.ItemDetailScreen
import dev.septianbeneran.technicaltest.feature.a.screen.ItemListRoute
import javax.inject.Inject

class ANavGraphImpl @Inject constructor(): BaseNavGraph {
    override fun NavGraphBuilder.createGraph(navController: NavController) {
        composable<ItemListRoute> {
            ItemListRoute(
                onItemClick = { item ->
                    navController.navigate(ItemDetailRoute(item))
                }
            )
        }
        composable<ItemDetailRoute>(
            typeMap = mapOf(typeMapOf<Item>())
        ) {
            ItemDetailScreen()
        }
    }
}

fun SavedStateHandle.getItemDetailRouteParams(): ItemDetailRoute = toRoute<ItemDetailRoute>(
    typeMap = mapOf(
        typeMapOf<Item>()
    )
)