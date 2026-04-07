package dev.septianbeneran.technicaltest.core.base

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

interface BaseNavGraph {
    fun NavGraphBuilder.createGraph(navController: NavController)
}