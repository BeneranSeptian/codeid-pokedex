package dev.septianbeneran.technicaltest.core.base

import androidx.navigation.NavGraphBuilder
import dev.septianbeneran.technicaltest.core.navigation.util.Navigator

interface BaseNavGraph {
    fun NavGraphBuilder.createGraph(navigator: Navigator)
}