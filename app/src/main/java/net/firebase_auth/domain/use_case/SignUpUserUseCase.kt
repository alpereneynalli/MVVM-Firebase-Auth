package net.firebase_auth.domain.use_case

import net.firebase_auth.domain.repository.AuthRepository
import net.firebase_auth.ui.auth.AuthState
import javax.inject.Inject

class SignUpUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(name: String, email: String, password: String): AuthState {
        return authRepository.signup(name, email, password)
    }
}