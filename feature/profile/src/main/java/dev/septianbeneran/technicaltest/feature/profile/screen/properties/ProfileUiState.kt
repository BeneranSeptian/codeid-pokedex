package dev.septianbeneran.technicaltest.feature.profile.screen.properties

import dev.septianbeneran.technicaltest.core.entity.model.auth.User

data class ProfileUiState(
    val user: User? = null,
    val isLoggedOut: Boolean = false
)

sealed interface ProfileAction {
    data object Logout : ProfileAction
}