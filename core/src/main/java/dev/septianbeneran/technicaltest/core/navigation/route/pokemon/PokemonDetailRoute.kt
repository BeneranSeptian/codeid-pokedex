package dev.septianbeneran.technicaltest.core.navigation.route.pokemon

import kotlinx.serialization.Serializable

@Serializable
data class PokemonDetailRoute(
    val pokemonId: Int,
    val pokemonName: String
)