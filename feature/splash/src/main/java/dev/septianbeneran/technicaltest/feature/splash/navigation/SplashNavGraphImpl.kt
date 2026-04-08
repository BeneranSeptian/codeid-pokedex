package dev.septianbeneran.technicaltest.feature.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.septianbeneran.technicaltest.core.base.BaseNavGraph
import dev.septianbeneran.technicaltest.core.navigation.graph.SplashGraphRoute
import dev.septianbeneran.technicaltest.core.navigation.route.splash.SplashRoute
import dev.septianbeneran.technicaltest.core.navigation.util.Navigator
import dev.septianbeneran.technicaltest.feature.splash.screen.SplashScreen
import javax.inject.Inject

class SplashNavGraphImpl @Inject constructor() : BaseNavGraph {
    override fun NavGraphBuilder.createGraph(navigator: Navigator) {
        navigation<SplashGraphRoute>(
            startDestination = SplashRoute
        ) {
            composable<SplashRoute> {
                SplashScreen(navigator = navigator)
            }
        }
    }
}