package com.example.taskmaster.di

import com.example.taskmaster.domain.useCases.auth.FakeGetAccessTokenUseCase
import com.example.taskmaster.domain.useCases.auth.FakeLoginUseCaseImp
import com.example.taskmaster.domain.useCases.auth.FakeLogoutUseCase
import com.example.taskmaster.domain.useCases.auth.GetAccessTokenUseCase
import com.example.taskmaster.domain.useCases.auth.LoginUseCase
import com.example.taskmaster.domain.useCases.auth.LogoutUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [UseCaseModule::class]
)
object FakeUseCaseModule {

    @Provides
    fun provideFakeLoginUseCase(): LoginUseCase = FakeLoginUseCaseImp()

    @Provides
    fun provideFakeAccessTokenUseCase(): GetAccessTokenUseCase = FakeGetAccessTokenUseCase()

    @Provides
    fun provideFakeLogoutUseCase(): LogoutUseCase = FakeLogoutUseCase()
}
