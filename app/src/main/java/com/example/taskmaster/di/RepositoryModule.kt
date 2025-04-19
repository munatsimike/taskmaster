package com.example.taskmaster.di

import com.example.taskmaster.data.local.LocalDataSourceImpl
import com.example.taskmaster.data.remote.RemoteDataSourceImpl
import com.example.taskmaster.data.repository.AuthRepositoryImp
import com.example.taskmaster.domain.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

// RepositoryModule provides dependencies related to repositories within the application.
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    // provide dependency for AuthRepository
    @Provides
    fun provideAuthRepo(userDataSource: RemoteDataSourceImpl, localDataSource: LocalDataSourceImpl): AuthRepository =
        AuthRepositoryImp(userDataSource, localDataSource)
}