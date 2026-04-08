package dev.septianbeneran.technicaltest.api.pokemon.usecase

import dev.septianbeneran.technicaltest.api.pokemon.repository.PokemonRepository
import dev.septianbeneran.technicaltest.core.entity.model.pokemon.Pokemon
import dev.septianbeneran.technicaltest.core.entity.model.pokemon.PokemonDetail
import dev.septianbeneran.technicaltest.core.entity.remote.ApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonDetailUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    operator fun invoke(nameOrId: String): Flow<ApiResult<PokemonDetail>> =
        repository.getPokemonDetail(nameOrId)
}
