package dev.septianbeneran.technicaltest.feature.pokemon.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.septianbeneran.technicaltest.core.base.BaseNavGraph
import dev.septianbeneran.technicaltest.core.navigation.graph.PokemonGraphRoute
import dev.septianbeneran.technicaltest.core.navigation.route.pokemon.PokemonDetailRoute
import dev.septianbeneran.technicaltest.core.navigation.route.pokemon.PokemonListRoute
import dev.septianbeneran.technicaltest.core.navigation.util.Navigator
import dev.septianbeneran.technicaltest.feature.pokemon.screen.PokemonDetailScreenRoute
import dev.septianbeneran.technicaltest.feature.pokemon.screen.PokemonListScreenRoute
import javax.inject.Inject

class PokemonNavGraphImpl @Inject constructor() : BaseNavGraph {
    override fun NavGraphBuilder.createGraph(navigator: Navigator) {
        navigation<PokemonGraphRoute>(
            startDestination = PokemonListRoute
        ) {
            composable<PokemonListRoute> {
                PokemonListScreenRoute(navigator = navigator)
            }

            composable<PokemonDetailRoute> {
                PokemonDetailScreenRoute(navigator = navigator)
            }
        }
    }
}