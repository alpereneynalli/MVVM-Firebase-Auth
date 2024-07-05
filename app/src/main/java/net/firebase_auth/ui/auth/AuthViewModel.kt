package net.firebase_auth.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.firebase_auth.data.AuthRepository
import net.firebase_auth.data.AuthRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _authState = MutableLiveData<AuthState?>()
    val authState: LiveData<AuthState?> = _authState

    val currentUser: FirebaseUser?
        get() = authRepository.currentUser

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        if (currentUser != null) {
            _authState.value = AuthState.Authenticated
            Log.d("AuthViewModel", "User is authenticated")
        } else {
            _authState.value = AuthState.Unauthenticated
            Log.d("AuthViewModel", "User is unauthorized")
        }
    }

    fun resetErrorState() {
        if (_authState.value is AuthState.Error) {
            _authState.value = AuthState.Unauthenticated
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = authRepository.login(email, password)
            _authState.value = result
        }
    }

    fun signUp(name: String, email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = authRepository.signup(name, email, password)
            _authState.value = result
        }
    }

    fun logout() {
        authRepository.logout()
        _authState.value = AuthState.Unauthenticated
    }

}