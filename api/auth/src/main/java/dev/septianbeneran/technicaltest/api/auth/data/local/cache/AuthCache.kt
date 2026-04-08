package dev.septianbeneran.technicaltest.api.auth.data.local.cache

import dev.septianbeneran.technicaltest.core.entity.model.auth.User

interface AuthCache {
    fun registerUser(user: User): Boolean
    fun findUserByEmail(email: String): User?
    fun loginUser(email: String, password: String): Boolean
    fun updatePassword(email: String, newPassword: String): Boolean
}