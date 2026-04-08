package dev.septianbeneran.technicaltest.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.septianbeneran.technicaltest.core.base.BaseNavGraph
import dev.septianbeneran.technicaltest.core.navigation.graph.AuthGraphRoute
import dev.septianbeneran.technicaltest.core.navigation.route.home.ProfileRoute
import dev.septianbeneran.technicaltest.core.navigation.util.Navigator
import javax.inject.Inject

class HomeNavGraphImpl @Inject constructor() : BaseNavGraph {
    override fun NavGraphBuilder.createGraph(navigator: Navigator) {
        navigation<AuthGraphRoute>(
            startDestination = ProfileRoute
        ) {
            composable<ProfileRoute> {

            }
        }
    }

}