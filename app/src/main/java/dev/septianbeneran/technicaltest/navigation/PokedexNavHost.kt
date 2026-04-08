package dev.septianbeneran.technicaltest.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.septianbeneran.technicaltest.core.base.BaseNavGraph
import dev.septianbeneran.technicaltest.core.navigation.graph.PokemonGraphRoute
import dev.septianbeneran.technicaltest.core.navigation.graph.ProfileGraphRoute
import dev.septianbeneran.technicaltest.core.navigation.graph.SplashGraphRoute
import dev.septianbeneran.technicaltest.core.navigation.route.pokemon.PokemonListRoute
import dev.septianbeneran.technicaltest.core.navigation.route.profile.ProfileRoute
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

    val bottomBarRoutes = setOf(
        PokemonListRoute::class,
        ProfileRoute::class
    )

    val showBottomBar = bottomBarRoutes.any {
            currentDestination?.hasRoute(it) == true
        }

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    tonalElevation = 8.dp
                ) {
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
                                        inclusive = false,
                                        saveState = true,
                                        restoreState = true,
                                        launchSingleTop = true
                                    )
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.label
                                )
                            },
                            label = { Text(text = item.label) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                indicatorColor = MaterialTheme.colorScheme.primaryContainer
                            )
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
