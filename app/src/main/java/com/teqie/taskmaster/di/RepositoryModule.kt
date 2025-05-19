package com.teqie.taskmaster.di

import com.teqie.taskmaster.data.local.LocalDataSource
import com.teqie.taskmaster.data.remote.RemoteDataSource
import com.teqie.taskmaster.data.repository.AuthRepositoryImp
import com.teqie.taskmaster.data.repository.DashboardRepositoryImpl
import com.teqie.taskmaster.data.repository.FileManagementRepoImpl
import com.teqie.taskmaster.data.repository.ProjectsRepoImpl
import com.teqie.taskmaster.data.repository.TeamsRepositoryImpl
import com.teqie.taskmaster.data.repository.budget.BudgetPhaseRepositoryImpl
import com.teqie.taskmaster.data.repository.budget.InvoiceRepositoryImpl
import com.teqie.taskmaster.data.repository.gallery.GalleryRepository
import com.teqie.taskmaster.data.repository.gallery.GalleryRepositoryImpl
import com.teqie.taskmaster.data.repository.schedule.ScheduleRepository
import com.teqie.taskmaster.data.repository.schedule.ScheduleRepositoryImpl
import com.teqie.taskmaster.domain.AuthRepository
import com.teqie.taskmaster.domain.DashboardRepository
import com.teqie.taskmaster.domain.ProjectRepository
import com.teqie.taskmaster.domain.model.budget.BudgetPhaseRepository
import com.teqie.taskmaster.domain.model.budget.InvoiceRepository
import com.teqie.taskmaster.domain.model.file.FileManagementRepository
import com.teqie.taskmaster.domain.teams.TeamsRepository
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
        userDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
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

    @Provides
    fun provideFileManagementRepo(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): FileManagementRepository =
        FileManagementRepoImpl(remoteDataSource,localDataSource)

    @Provides
    fun provideInvoiceRepo(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): InvoiceRepository =
        InvoiceRepositoryImpl(localDataSource, remoteDataSource)

    @Provides
    fun provideTeamsRepo(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): TeamsRepository =
        TeamsRepositoryImpl(localDataSource, remoteDataSource)

    @Provides
    fun provideScheduleRepo(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): ScheduleRepository =
        ScheduleRepositoryImpl(localDataSource, remoteDataSource)

    @Provides
    fun provideGalleryRepo(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): GalleryRepository =
        GalleryRepositoryImpl(localDataSource, remoteDataSource)
}