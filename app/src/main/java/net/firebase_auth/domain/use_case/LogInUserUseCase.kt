package net.firebase_auth.domain.use_case

import net.firebase_auth.domain.repository.AuthRepository
import net.firebase_auth.ui.auth.AuthState
import javax.inject.Inject

class LogInUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): AuthState {
        return authRepository.login(email, password)
    }
}