package dev.septianbeneran.technicaltest.feature.splash.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.septianbeneran.technicaltest.api.auth.data.local.cache.SessionCache
import dev.septianbeneran.technicaltest.core.base.BaseViewModel
import dev.septianbeneran.technicaltest.feature.splash.screen.properties.SplashEvent.NavigateToAuth
import dev.septianbeneran.technicaltest.feature.splash.screen.properties.SplashEvent.NavigateToHome
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sessionCache: SessionCache
) : BaseViewModel() {

    init {
        checkSession()
    }

    private fun checkSession() {
        viewModelScope.launch {
            delay(2000)
            val isLoggedIn = sessionCache.isLoggedIn().first()
            if (isLoggedIn) {
                sendEvent(NavigateToHome)
            } else {
                sendEvent(NavigateToAuth)
            }
        }
    }
}
