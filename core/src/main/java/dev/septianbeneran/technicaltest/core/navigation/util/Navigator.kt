package dev.septianbeneran.technicaltest.core.navigation.util

import androidx.navigation.NavHostController
import androidx.navigation.toRoute

class Navigator(
    val navController: NavHostController
) {
    fun navigate(
        route: Any,
        popUpTo: Any? = null,
        inclusive: Boolean = false
    ) {
        navController.navigate(route) {
            popUpTo?.let {
                popUpTo(it) {
                    this.inclusive = inclusive
                }
            }
        }
    }

    fun navigateUp() = navController.navigateUp()

    inline fun <reified T> getCurrentRoute() = navController.currentBackStackEntry?.toRoute<T>()
}