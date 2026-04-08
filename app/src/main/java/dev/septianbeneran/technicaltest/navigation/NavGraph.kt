package dev.septianbeneran.technicaltest.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.septianbeneran.technicaltest.core.base.BaseNavGraph
import dev.septianbeneran.technicaltest.core.navigation.graph.SplashGraphRoute
import dev.septianbeneran.technicaltest.core.navigation.route.auth.LoginRoute
import dev.septianbeneran.technicaltest.core.navigation.util.Navigator

@Composable
fun TechnicalTestNavGraph(
    navController: NavHostController,
    navGraphs: Set<BaseNavGraph>,
    innerPadding: PaddingValues
) {
    val navigator = remember {
        Navigator(navController)
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val isLoginScreen = currentRoute?.contains(LoginRoute::class.qualifiedName.toString()) == true

    NavHost(
        navController = navController,
        startDestination = SplashGraphRoute,
        modifier = Modifier.padding(if (isLoginScreen) PaddingValues() else innerPadding)
    ) {
        navGraphs.forEach { graph ->
            with(graph) { createGraph(navigator) }
        }
    }
}
