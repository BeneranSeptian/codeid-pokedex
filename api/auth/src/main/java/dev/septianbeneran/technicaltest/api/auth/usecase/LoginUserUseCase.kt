package dev.septianbeneran.technicaltest.api.auth.usecase

import dev.septianbeneran.technicaltest.api.auth.repository.JsonBinRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val repository: JsonBinRepository
) {
    operator fun invoke(email: String, password: String) = repository.authCache.loginUser(email, password)
}