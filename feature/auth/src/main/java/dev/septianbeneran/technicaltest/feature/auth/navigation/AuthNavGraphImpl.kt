package dev.septianbeneran.technicaltest.feature.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.septianbeneran.technicaltest.core.base.BaseNavGraph
import dev.septianbeneran.technicaltest.core.navigation.graph.AuthGraphRoute
import dev.septianbeneran.technicaltest.core.navigation.route.auth.ForgotPasswordRoute
import dev.septianbeneran.technicaltest.core.navigation.route.auth.LoginRoute
import dev.septianbeneran.technicaltest.core.navigation.route.auth.RegisterRoute
import dev.septianbeneran.technicaltest.core.navigation.util.Navigator
import dev.septianbeneran.technicaltest.feature.auth.screen.ForgotPasswordScreenRoute
import dev.septianbeneran.technicaltest.feature.auth.screen.LoginScreenRoute
import dev.septianbeneran.technicaltest.feature.auth.screen.RegisterScreenRoute
import javax.inject.Inject

class AuthNavGraphImpl @Inject constructor(): BaseNavGraph {
    override fun NavGraphBuilder.createGraph(navigator: Navigator) {

        navigation<AuthGraphRoute>(
            startDestination = LoginRoute()
        ) {
            composable<LoginRoute> {
                LoginScreenRoute(navigator)
            }

            composable<RegisterRoute> {
                RegisterScreenRoute(navigator)
            }

            composable<ForgotPasswordRoute> {
                ForgotPasswordScreenRoute(navigator)
            }
        }
    }
}
