package dev.septianbeneran.technicaltest.api.auth.usecase

import dev.septianbeneran.technicaltest.api.auth.repository.JsonBinRepository
import javax.inject.Inject

class UpdatePasswordUseCase @Inject constructor(
    val repository: JsonBinRepository
) {
    operator fun invoke(email: String, newPassword: String) =
        repository.authCache.updatePassword(email, newPassword)
}