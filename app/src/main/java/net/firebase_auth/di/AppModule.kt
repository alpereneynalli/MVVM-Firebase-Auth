package net.firebase_auth.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.firebase_auth.data.repository.AuthRepositoryImpl
import net.firebase_auth.domain.repository.AuthRepository
import net.firebase_auth.domain.use_case.GetCurrentUserUseCase
import net.firebase_auth.domain.use_case.LogInUserUseCase
import net.firebase_auth.domain.use_case.LogOutUserUseCase
import net.firebase_auth.domain.use_case.SignUpUserUseCase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Provides
    @Singleton
    fun provideLogInUser(authRepository: AuthRepository): LogInUserUseCase {
        return LogInUserUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideSignUpUser(authRepository: AuthRepository): SignUpUserUseCase {
        return SignUpUserUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideLogOutUser(authRepository: AuthRepository): LogOutUserUseCase {
        return LogOutUserUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideGetCurrentUser(authRepository: AuthRepository): GetCurrentUserUseCase {
        return GetCurrentUserUseCase(authRepository)
    }
}