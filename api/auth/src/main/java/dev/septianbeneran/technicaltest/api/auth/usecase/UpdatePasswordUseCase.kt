package dev.septianbeneran.technicaltest.api.auth.usecase

import dev.septianbeneran.technicaltest.api.auth.repository.AuthRepository
import dev.septianbeneran.technicaltest.core.util.HashUtil.hashString
import javax.inject.Inject

class UpdatePasswordUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(email: String, newPassword: String): Result {
        val user = repository.authCache.findUserByEmail(email) ?: return Result.EmailNotRegistered
        
        if (user.password == hashString(newPassword)) {
            return Result.SameAsCurrentPassword
        }

        val success = repository.authCache.updatePassword(email, newPassword)
        return if (success) Result.Success else Result.Failure
    }

    sealed interface Result {
        data object Success : Result
        data object EmailNotRegistered : Result
        data object SameAsCurrentPassword : Result
        data object Failure : Result
    }
}
