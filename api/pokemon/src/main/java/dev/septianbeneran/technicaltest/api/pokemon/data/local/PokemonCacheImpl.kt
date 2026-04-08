package dev.septianbeneran.technicaltest.api.pokemon.data.local

import dev.septianbeneran.technicaltest.core.base.BaseCouchbase
import dev.septianbeneran.technicaltest.core.entity.model.pokemon.Pokemon
import dev.septianbeneran.technicaltest.core.entity.model.pokemon.PokemonDetail
import javax.inject.Inject

class PokemonCacheImpl @Inject constructor(
    private val baseCouchbase: BaseCouchbase
) : PokemonCache {
    override fun loadPokemonList(): List<Pokemon> {
        return baseCouchbase.getAllObjects("pokemon_list")
    }

    override fun updatePokemonList(pokemonList: List<Pokemon>) {
        pokemonList.forEach { pokemon ->
            baseCouchbase.saveObject("pokemon_list", pokemon.name, pokemon)
        }
    }

    override fun loadPokemonDetail(nameOrId: String): PokemonDetail? {
        return baseCouchbase.getObject("pokemon_detail", nameOrId)
    }

    override fun updatePokemonDetail(pokemonDetail: PokemonDetail) {
        baseCouchbase.saveObject("pokemon_detail", pokemonDetail.name, pokemonDetail)
        baseCouchbase.saveObject("pokemon_detail", pokemonDetail.id.toString(), pokemonDetail)
    }
}