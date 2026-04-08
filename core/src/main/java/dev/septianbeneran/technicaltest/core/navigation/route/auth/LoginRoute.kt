package dev.septianbeneran.technicaltest.core.navigation.route.auth

import kotlinx.serialization.Serializable

@Serializable
data class LoginRoute(
    val email: String? = null
)
