package dev.septianbeneran.technicaltest.core.navigation.util

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy

inline fun <reified T : Any> NavDestination?.isRoute(): Boolean {
    return this
        ?.hierarchy
        ?.any { it.hasRoute(T::class) } == true
}