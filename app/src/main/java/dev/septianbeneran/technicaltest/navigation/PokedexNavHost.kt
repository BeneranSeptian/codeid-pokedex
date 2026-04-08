package dev.septianbeneran.technicaltest.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.septianbeneran.technicaltest.core.base.BaseNavGraph
import dev.septianbeneran.technicaltest.core.navigation.graph.PokemonGraphRoute
import dev.septianbeneran.technicaltest.core.navigation.graph.ProfileGraphRoute
import dev.septianbeneran.technicaltest.core.navigation.graph.SplashGraphRoute
import dev.septianbeneran.technicaltest.core.navigation.util.Navigator
import dev.septianbeneran.technicaltest.core.navigation.util.isRoute
import dev.septianbeneran.technicaltest.navigation.BottomNavItem.PokemonList
import dev.septianbeneran.technicaltest.navigation.BottomNavItem.Profile

@Composable
fun PokedexNavHost(
    navController: NavHostController,
    navGraphs: Set<BaseNavGraph>,
) {
    val navigator = remember {
        Navigator(navController)
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val currentTabRoute = when {
        currentDestination.isRoute<PokemonGraphRoute>() ->
            PokemonGraphRoute

        currentDestination.isRoute<ProfileGraphRoute>() ->
            ProfileGraphRoute

        else -> null
    }

    val bottomNavItems = listOf(
        PokemonList,
        Profile
    )

    val showBottomBar = bottomNavItems.any { item ->
        currentDestination
            ?.hierarchy
            ?.any {
                it.hasRoute(item.route::class)
            } == true

    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        val isSelected = currentDestination
                                ?.hierarchy
                                ?.any { it.hasRoute(item.route::class) } == true

                        NavigationBarItem(
                            selected = isSelected,
                            onClick = {
                                if (!isSelected) {
                                    navigator.navigate(
                                        route = item.route,
                                        popUpTo = currentTabRoute,
                                        inclusive = false
                                    )
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.label
                                )
                            },
                            label = { Text(text = item.label) }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = SplashGraphRoute,
            modifier = Modifier.padding(paddingValues)
        ) {
            navGraphs.forEach { graph ->
                with(graph) { createGraph(navigator) }
            }
        }
    }
}
