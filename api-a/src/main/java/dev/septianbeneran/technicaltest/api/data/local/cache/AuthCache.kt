package dev.septianbeneran.technicaltest.api.data.local.cache

import dev.septianbeneran.technicaltest.core.entity.model.User

interface AuthCache {
    fun registerUser(user: User): Boolean
    fun findUserByEmail(email: String): User?
    fun loginUser(email: String, password: String): Boolean
}