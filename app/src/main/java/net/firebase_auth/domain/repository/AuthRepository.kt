package net.firebase_auth.domain.repository

import com.google.firebase.auth.FirebaseUser
import net.firebase_auth.ui.auth.AuthState

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): AuthState
    suspend fun signup(name: String, email: String, password: String): AuthState
    fun logout(): AuthState
}