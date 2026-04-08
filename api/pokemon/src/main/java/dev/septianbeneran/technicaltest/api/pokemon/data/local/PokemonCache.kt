package dev.septianbeneran.technicaltest.api.pokemon.data.local

import dev.septianbeneran.technicaltest.core.entity.model.pokemon.Pokemon

interface PokemonCache {
    fun loadPokemonList(): List<Pokemon>
    fun updatePokemonList(pokemonList: List<Pokemon>)
}