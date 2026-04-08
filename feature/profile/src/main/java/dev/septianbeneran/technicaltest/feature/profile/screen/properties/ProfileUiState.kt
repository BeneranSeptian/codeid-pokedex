package dev.septianbeneran.technicaltest.feature.profile.screen.properties

import dev.septianbeneran.technicaltest.core.entity.model.auth.User

data class ProfileUiState(
    val user: User? = null,
    val isLoggedOut: Boolean = false,
    val profilePictureUrl: String? = null
)

sealed interface ProfileAction {
    data object Logout : ProfileAction
}