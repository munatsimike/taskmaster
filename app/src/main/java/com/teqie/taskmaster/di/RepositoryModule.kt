package com.teqie.taskmaster.di

import com.teqie.taskmaster.data.local.LocalDataSource
import com.teqie.taskmaster.data.local.LocalDataSourceImpl
import com.teqie.taskmaster.data.remote.RemoteDataSource
import com.teqie.taskmaster.data.remote.RemoteDataSourceImpl
import com.teqie.taskmaster.data.repository.AuthRepositoryImp
import com.teqie.taskmaster.data.repository.BudgetPhaseRepositoryImpl
import com.teqie.taskmaster.data.repository.DashboardRepositoryImpl
import com.teqie.taskmaster.data.repository.ProjectsRepoImpl
import com.teqie.taskmaster.domain.AuthRepository
import com.teqie.taskmaster.domain.BudgetPhaseRepository
import com.teqie.taskmaster.domain.DashboardRepository
import com.teqie.taskmaster.domain.ProjectRepository
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

    @Provides
    fun provideBudgetPhaseRepo(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): BudgetPhaseRepository =
        BudgetPhaseRepositoryImpl(localDataSource, remoteDataSource)
}