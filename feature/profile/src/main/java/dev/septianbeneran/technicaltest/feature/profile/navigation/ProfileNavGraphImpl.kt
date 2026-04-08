package dev.septianbeneran.technicaltest.feature.profile.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.septianbeneran.technicaltest.core.base.BaseNavGraph
import dev.septianbeneran.technicaltest.core.navigation.graph.ProfileGraphRoute
import dev.septianbeneran.technicaltest.core.navigation.route.home.ProfileRoute
import dev.septianbeneran.technicaltest.core.navigation.util.Navigator
import dev.septianbeneran.technicaltest.feature.profile.screen.ProfileScreenRoute
import javax.inject.Inject

class ProfileNavGraphImpl @Inject constructor() : BaseNavGraph {
    override fun NavGraphBuilder.createGraph(navigator: Navigator) {
        navigation<ProfileGraphRoute>(
            startDestination = ProfileRoute
        ) {
            composable<ProfileRoute> {
                ProfileScreenRoute(navigator = navigator)
            }
        }
    }
}