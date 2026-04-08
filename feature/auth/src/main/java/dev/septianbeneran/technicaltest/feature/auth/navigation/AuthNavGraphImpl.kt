package dev.septianbeneran.technicaltest.feature.auth.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import dev.septianbeneran.technicaltest.core.base.BaseNavGraph
import dev.septianbeneran.technicaltest.core.entity.model.Item
import dev.septianbeneran.technicaltest.core.navigation.graph.AuthGraphRoute
import dev.septianbeneran.technicaltest.core.navigation.route.auth.ForgotPasswordRoute
import dev.septianbeneran.technicaltest.core.navigation.route.auth.ItemDetailRoute
import dev.septianbeneran.technicaltest.core.navigation.route.auth.ItemListRoute
import dev.septianbeneran.technicaltest.core.navigation.route.auth.LoginRoute
import dev.septianbeneran.technicaltest.core.navigation.route.auth.RegisterRoute
import dev.septianbeneran.technicaltest.core.navigation.util.Navigator
import dev.septianbeneran.technicaltest.core.util.navtype.typeMapOf
import dev.septianbeneran.technicaltest.feature.auth.screen.ForgotPasswordScreenRoute
import dev.septianbeneran.technicaltest.feature.auth.screen.ItemDetailScreen
import dev.septianbeneran.technicaltest.feature.auth.screen.ItemListRoute
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

            composable<ItemListRoute> {
                ItemListRoute(
                    onItemClick = { item ->
                        navigator.navigate(ItemDetailRoute(item))
                    }
                )
            }

            composable<ItemDetailRoute>(
                typeMap = mapOf(typeMapOf<Item>())
            ) {
                ItemDetailScreen()
            }
        }
    }
}

fun SavedStateHandle.getItemDetailRouteParams(): ItemDetailRoute = toRoute<ItemDetailRoute>(
    typeMap = mapOf(
        typeMapOf<Item>()
    )
)
