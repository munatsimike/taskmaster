package com.example.taskmaster.di

import com.example.taskmaster.data.local.LocalDataSource
import com.example.taskmaster.data.local.LocalDataSourceImpl
import com.example.taskmaster.data.remote.RemoteDataSource
import com.example.taskmaster.data.remote.RemoteDataSourceImpl
import com.example.taskmaster.data.repository.AuthRepositoryImp
import com.example.taskmaster.data.repository.DashboardRepositoryImpl
import com.example.taskmaster.data.repository.ProjectsRepoImpl
import com.example.taskmaster.domain.AuthRepository
import com.example.taskmaster.domain.DashboardRepository
import com.example.taskmaster.domain.ProjectRepository
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
    fun provideAuthRepo(
        userDataSource: RemoteDataSourceImpl,
        localDataSource: LocalDataSourceImpl
    ): AuthRepository =
        AuthRepositoryImp(userDataSource, localDataSource)

    @Provides
    fun provideProjectRepo(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): ProjectRepository =
        ProjectsRepoImpl(remoteDataSource, localDataSource)

    @Provides
    fun provideDashboardRepo(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): DashboardRepository =
        DashboardRepositoryImpl(remoteDataSource, localDataSource)
}