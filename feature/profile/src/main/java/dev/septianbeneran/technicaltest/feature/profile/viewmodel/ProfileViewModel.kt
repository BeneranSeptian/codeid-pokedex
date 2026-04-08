package dev.septianbeneran.technicaltest.feature.profile.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.septianbeneran.technicaltest.api.auth.data.local.cache.AuthCache
import dev.septianbeneran.technicaltest.api.auth.data.local.cache.SessionCache
import dev.septianbeneran.technicaltest.core.base.BaseViewModel
import dev.septianbeneran.technicaltest.feature.profile.screen.properties.ProfileAction
import dev.septianbeneran.technicaltest.feature.profile.screen.properties.ProfileAction.Logout
import dev.septianbeneran.technicaltest.feature.profile.screen.properties.ProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val sessionCache: SessionCache,
    private val authCache: AuthCache
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadUserProfile()
    }

    fun onAction(action: ProfileAction) {
        when (action) {
            Logout -> logout()
        }
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            val email = sessionCache.loadLoggedInEmail().first()
            if (email != null) {
                val user = authCache.findUserByEmail(email)
                _uiState.update { it.copy(user = user) }
            }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            sessionCache.logout()
            _uiState.update { it.copy(isLoggedOut = true) }
        }
    }
}