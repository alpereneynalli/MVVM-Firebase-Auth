package net.firebase_auth.domain.use_case

import com.google.firebase.auth.FirebaseUser
import net.firebase_auth.domain.repository.AuthRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): FirebaseUser? {
        return authRepository.currentUser
    }
}