package dev.septianbeneran.technicaltest.api.pokemon.data.local

import dev.septianbeneran.technicaltest.core.entity.model.pokemon.Pokemon
import dev.septianbeneran.technicaltest.core.entity.model.pokemon.PokemonDetail

interface PokemonCache {
    fun loadPokemonList(): List<Pokemon>
    fun updatePokemonList(pokemonList: List<Pokemon>)
    fun loadPokemonDetail(nameOrId: String): PokemonDetail?
    fun updatePokemonDetail(pokemonDetail: PokemonDetail)
}