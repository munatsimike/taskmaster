package com.teqie.taskmaster.di

import com.teqie.taskmaster.data.repository.AuthRepositoryImp
import com.teqie.taskmaster.domain.AuthRepository
import com.teqie.taskmaster.domain.useCases.auth.GetAccessTokenUseCase
import com.teqie.taskmaster.domain.useCases.auth.GetAccessTokenUseCaseImp
import com.teqie.taskmaster.domain.useCases.auth.LoginUseCase
import com.teqie.taskmaster.domain.useCases.auth.LoginUseCaseImp
import com.teqie.taskmaster.domain.useCases.auth.LogoutUseCaseImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideLoginUseCase(repo: AuthRepository): LoginUseCase {
        return LoginUseCaseImp(repo)
    }

    @Provides
    fun provideFakeAccessTokenUseCase(authRepository: AuthRepositoryImp): GetAccessTokenUseCase =
        GetAccessTokenUseCaseImp(
            authRepository
        )

    @Provides
    fun provideFakeLogoutUseCase(authRepository: AuthRepositoryImp): LogoutUseCaseImp = LogoutUseCaseImp(authRepository)
}
