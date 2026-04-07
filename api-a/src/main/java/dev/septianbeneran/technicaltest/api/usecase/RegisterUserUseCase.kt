package dev.septianbeneran.technicaltest.api.usecase

import dev.septianbeneran.technicaltest.api.repository.JsonBinRepository
import dev.septianbeneran.technicaltest.core.entity.model.User
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val repository: JsonBinRepository
) {
    operator fun invoke(user: User) = repository.authCache.registerUser(user)
}