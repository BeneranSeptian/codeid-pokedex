package dev.septianbeneran.technicaltest.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CatchingPokemon
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import dev.septianbeneran.technicaltest.core.navigation.graph.PokemonGraphRoute
import dev.septianbeneran.technicaltest.core.navigation.graph.ProfileGraphRoute

sealed class BottomNavItem(val route: Any, val label: String, val icon: ImageVector) {
    data object PokemonList : BottomNavItem(PokemonGraphRoute, "Pokemon", Icons.Default.CatchingPokemon)
    data object Profile : BottomNavItem(ProfileGraphRoute, "Profile", Icons.Default.Person)
}