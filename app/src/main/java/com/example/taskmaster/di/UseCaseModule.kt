package com.example.taskmaster.di

import com.example.taskmaster.data.repository.AuthRepository
import com.example.taskmaster.domain.DataRepository
import com.example.taskmaster.domain.useCases.auth.GetAccessTokenUseCase
import com.example.taskmaster.domain.useCases.auth.GetAccessTokenUseCaseImp
import com.example.taskmaster.domain.useCases.auth.LoginUseCase
import com.example.taskmaster.domain.useCases.auth.LoginUseCaseImp
import com.example.taskmaster.domain.useCases.auth.LogoutUseCaseImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideLoginUseCase(repo: DataRepository): LoginUseCase {
        return LoginUseCaseImp(repo)
    }

    @Provides
    fun provideFakeAccessTokenUseCase(authRepository: AuthRepository): GetAccessTokenUseCase =
        GetAccessTokenUseCaseImp(
            authRepository
        )

    @Provides
    fun provideFakeLogoutUseCase(authRepository: AuthRepository): LogoutUseCaseImp = LogoutUseCaseImp(authRepository)
}
