package dev.septianbeneran.technicaltest.feature.pokemon.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dev.septianbeneran.technicaltest.core.base.BaseScreen
import dev.septianbeneran.technicaltest.core.navigation.util.Navigator
import dev.septianbeneran.technicaltest.core.ui.util.EventObserver
import dev.septianbeneran.technicaltest.feature.pokemon.viewmodel.PokemonListViewModel

@Composable
fun PokemonListScreenRoute(
    navigator: Navigator
) {
    val viewModel: PokemonListViewModel = hiltViewModel()
    BaseScreen(
        viewModel = viewModel,
    ) {
        Text("this is pokemon list screen")
    }

    EventObserver(
        event = viewModel.event
    ) { event ->

    }
}