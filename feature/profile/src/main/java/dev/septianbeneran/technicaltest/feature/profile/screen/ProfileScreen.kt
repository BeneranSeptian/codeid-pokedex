package dev.septianbeneran.technicaltest.feature.profile.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dev.septianbeneran.technicaltest.core.base.BaseScreen
import dev.septianbeneran.technicaltest.core.navigation.util.Navigator
import dev.septianbeneran.technicaltest.core.ui.util.EventObserver
import dev.septianbeneran.technicaltest.feature.profile.viewmodel.ProfileViewModel

@Composable
fun ProfileScreenRoute(
    navigator: Navigator
) {
    val viewModel: ProfileViewModel = hiltViewModel()
    BaseScreen(
        viewModel = viewModel,
    ) {
        Text("this is profile screen")
    }

    EventObserver(
        event = viewModel.event
    ) { event ->

    }
}