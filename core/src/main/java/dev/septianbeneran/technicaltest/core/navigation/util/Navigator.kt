package dev.septianbeneran.technicaltest.core.navigation.util

import androidx.navigation.NavHostController
import androidx.navigation.toRoute

class Navigator(
    val navController: NavHostController
) {
    fun navigate(
        route: Any,
        popUpTo: Any? = null,
        inclusive: Boolean = false,
        saveState: Boolean = false,
        restoreState: Boolean = false,
        launchSingleTop: Boolean = false
    ) {
        navController.navigate(route) {
            popUpTo?.let {
                when (it) {
                    is Int -> {
                        popUpTo(it) {
                            this.inclusive = inclusive
                            this.saveState = saveState
                        }
                    }

                    else -> {
                        popUpTo(it) {
                            this.inclusive = inclusive
                            this.saveState = saveState
                        }
                    }
                }
            }

            this.launchSingleTop = launchSingleTop
            this.restoreState = restoreState
        }
    }

    fun clearBackStackAndNavigate(route: Any) {
        navController.navigate(route) {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }

    fun navigateUp() = navController.navigateUp()

    inline fun <reified T> getCurrentRoute() = navController.currentBackStackEntry?.toRoute<T>()
}