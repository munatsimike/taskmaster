package com.teqie.taskmaster.di

import android.content.Context
import androidx.room.Room
import com.teqie.taskmaster.data.local.db.AppDatabase
import com.teqie.taskmaster.data.local.db.dao.BudgetPhaseDao
import com.teqie.taskmaster.data.local.db.dao.DashboardDao
import com.teqie.taskmaster.data.local.db.dao.GalleryImageDao
import com.teqie.taskmaster.data.local.db.dao.InvoiceDao
import com.teqie.taskmaster.data.local.db.dao.LoggedInUserDao
import com.teqie.taskmaster.data.local.db.dao.OrfiDao
import com.teqie.taskmaster.data.local.db.dao.ProjectDao
import com.teqie.taskmaster.data.local.db.dao.ScheduleDao
import com.teqie.taskmaster.data.local.db.dao.TeamMemberDao

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context = context, klass = AppDatabase::class.java, "app_d.").fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideLoggedInDao(database: AppDatabase): LoggedInUserDao = database.loggedInDao

    @Provides
    fun provideProjectsDao(database: AppDatabase): ProjectDao = database.projectDao

    @Provides
    fun provideDashboardDao(database: AppDatabase): DashboardDao = database.dashboardDao

    @Provides
    fun provideBudgetPhaseDao(database: AppDatabase): BudgetPhaseDao = database.budgetPhaseDao

    @Provides
    fun provideInvoiceDao(database: AppDatabase): InvoiceDao = database.invoiceDao

    @Provides
    fun provideTeamMemberDao(database: AppDatabase): TeamMemberDao = database.teamMemberDao

    @Provides
    fun provideScheduleDao(database: AppDatabase): ScheduleDao = database.scheduleDao

    @Provides
    fun provideGalleryDao(database: AppDatabase): GalleryImageDao = database.galleryDao

    @Provides
    fun provideOrfiDao(database: AppDatabase): OrfiDao = database.orfiDao
}