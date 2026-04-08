package dev.septianbeneran.technicaltest.feature.splash.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dev.septianbeneran.technicaltest.core.navigation.graph.PokemonGraphRoute
import dev.septianbeneran.technicaltest.core.navigation.route.auth.LoginRoute
import dev.septianbeneran.technicaltest.core.navigation.route.splash.SplashRoute
import dev.septianbeneran.technicaltest.core.navigation.util.Navigator
import dev.septianbeneran.technicaltest.core.ui.theme.Highlight
import dev.septianbeneran.technicaltest.core.ui.util.EventObserver
import dev.septianbeneran.technicaltest.feature.splash.screen.properties.SplashEvent
import dev.septianbeneran.technicaltest.feature.splash.viewmodel.SplashViewModel
import dev.septianbeneran.technicaltest.core.R as coreR

@Composable
fun SplashScreen(
    navigator: Navigator,
) {
    val viewModel: SplashViewModel = hiltViewModel()

    EventObserver(
        event = viewModel.event
    ) { event ->
        when (event) {
            is SplashEvent.NavigateToAuth -> {
                navigator.navigate(
                    route = LoginRoute(),
                    popUpTo = SplashRoute,
                    inclusive = true
                )
            }
            is SplashEvent.NavigateToHome -> {
                navigator.navigate(
                    route = PokemonGraphRoute,
                    popUpTo = SplashRoute,
                    inclusive = true
                )
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Highlight.Highlight100),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = coreR.drawable.pokedex_logo),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )
    }
}
