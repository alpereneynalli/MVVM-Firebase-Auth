package net.firebase_auth.domain.use_case

import net.firebase_auth.domain.repository.AuthRepository
import net.firebase_auth.ui.auth.AuthState
import javax.inject.Inject

class LogOutUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): AuthState {
        return authRepository.logout()
    }
}