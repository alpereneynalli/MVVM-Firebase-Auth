package net.firebase_auth.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import net.firebase_auth.domain.repository.AuthRepository
import net.firebase_auth.ui.auth.AuthState
import net.firebase_auth.utils.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    AuthRepository {
    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun login(email: String, password: String): AuthState {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            AuthState.Authenticated
        } catch (e: Exception) {
            AuthState.Error(e.message!!)
        }
    }

    override suspend fun signup(name: String, email: String, password: String): AuthState {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result.user?.updateProfile(
                UserProfileChangeRequest.Builder().setDisplayName(name).build()
            )?.await()
            AuthState.Authenticated
        } catch (e: Exception) {
            AuthState.Error(e.message!!)
        }
    }

    override fun logout(): AuthState {
        return try {
            firebaseAuth.signOut()
            AuthState.Unauthenticated
        } catch (e: Exception) {
            AuthState.Error(e.message!!)
        }
    }
}