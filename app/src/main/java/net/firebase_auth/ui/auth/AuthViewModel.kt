package net.firebase_auth.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.firebase_auth.domain.use_case.GetCurrentUserUseCase
import net.firebase_auth.domain.use_case.LogInUserUseCase
import net.firebase_auth.domain.use_case.LogOutUserUseCase
import net.firebase_auth.domain.use_case.SignUpUserUseCase
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val logInUserUseCase: LogInUserUseCase,
    private val logOutUserUseCase: LogOutUserUseCase,
    private val signUpUserUseCase: SignUpUserUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _authState = MutableLiveData<AuthState?>()
    val authState: LiveData<AuthState?> = _authState

    init {
        checkAuthStatus()
    }

    fun currentUser(): FirebaseUser? {
        return getCurrentUserUseCase()
    }

    fun checkAuthStatus() {
        if (currentUser() != null) {
            _authState.value = AuthState.Authenticated
        } else {
            _authState.value = AuthState.Unauthenticated
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
            _authState.value = logInUserUseCase(email, password)
        }
    }

    fun signUp(name: String, email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            _authState.value = signUpUserUseCase(name, email, password)
        }
    }

    fun logout() {
        _authState.value = logOutUserUseCase()
    }

}