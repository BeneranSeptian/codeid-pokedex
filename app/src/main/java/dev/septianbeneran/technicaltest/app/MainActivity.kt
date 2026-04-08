package dev.septianbeneran.technicaltest.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.septianbeneran.technicaltest.core.base.BaseNavGraph
import dev.septianbeneran.technicaltest.navigation.PokedexNavHost
import dev.septianbeneran.technicaltest.ui.theme.TechnicalTestTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navGraphs: Set<@JvmSuppressWildcards BaseNavGraph>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TechnicalTestTheme {
                val navController = rememberNavController()
                PokedexNavHost(
                    navController = navController,
                    navGraphs = navGraphs
                )
            }
        }
    }
}