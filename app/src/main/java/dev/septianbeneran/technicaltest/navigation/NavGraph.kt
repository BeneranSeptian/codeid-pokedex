package dev.septianbeneran.technicaltest.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dev.septianbeneran.technicaltest.core.base.BaseNavGraph
import dev.septianbeneran.technicaltest.core.navigation.route.ItemListRoute

@Composable
fun TechnicalTestNavGraph(
    navController: NavHostController,
    navGraphs: Set<BaseNavGraph>,
    innerPadding: PaddingValues
) {

    NavHost(
        navController = navController,
        startDestination = ItemListRoute,
        modifier = Modifier.padding(innerPadding)
    ) {
        navGraphs.forEach { graph ->
            with(graph) { createGraph(navController) }
        }
    }
}
