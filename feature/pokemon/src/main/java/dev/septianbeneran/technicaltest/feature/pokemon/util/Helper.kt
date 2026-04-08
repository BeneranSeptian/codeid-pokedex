package dev.septianbeneran.technicaltest.feature.pokemon.util

import androidx.compose.ui.graphics.Color

fun getPokemonImageUrl(pokemonUrl: String): String {
    val pokemonId = pokemonUrl
        .trimEnd('/')
        .substringAfterLast('/')

    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokemonId.png"
}

fun getPokemonOfficialArtWorkUrl(id: Int): String {
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
}

fun String.extractPokemonId(): Int {
    return this
        .trimEnd('/')
        .substringAfterLast('/')
        .toInt()
}

fun String.toPokemonTypeColor(): Color {
    return when (this.lowercase()) {
        "bug" -> Color(0xFFA7B723)
        "dark" -> Color(0xFF75574C)
        "dragon" -> Color(0xFF7037FF)
        "electric" -> Color(0xFFF9CF30)
        "fairy" -> Color(0xFFE69EAC)
        "fighting" -> Color(0xFFC12239)
        "fire" -> Color(0xFFF57D31)
        "flying" -> Color(0xFFA891EC)
        "ghost" -> Color(0xFF70559B)
        "normal" -> Color(0xFFAAA67F)
        "grass" -> Color(0xFF74CB48)
        "ground" -> Color(0xFFDEC16B)
        "ice" -> Color(0xFF9AD6DF)
        "poison" -> Color(0xFFA43E9E)
        "psychic" -> Color(0xFFFB5584)
        "rock" -> Color(0xFFB69E31)
        "steel" -> Color(0xFFB7B9D0)
        "water" -> Color(0xFF6493EB)
        else -> Color.Gray
    }
}