package com.example.taskmaster.di

import com.example.taskmaster.data.local.LocalDataSource
import com.example.taskmaster.data.remote.RemoteDataSource
import com.example.taskmaster.data.repository.AuthRepository
import com.example.taskmaster.domain.DataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

// RepositoryModule provides dependencies related to repositories within the application.
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    // provide dependency for DataRepository
    @Provides
    fun provideAuthRepo(userDataSource: RemoteDataSource, localDataSource: LocalDataSource): DataRepository =
        AuthRepository(userDataSource, localDataSource)
}