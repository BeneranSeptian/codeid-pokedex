package dev.septianbeneran.technicaltest.core.navigation.route.auth

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRoute(
    val email: String? = null
)
