package com.example.taskmaster.di

import android.content.Context
import androidx.room.Room
import com.example.taskmaster.data.local.db.AppDatabase
import com.example.taskmaster.data.local.db.dao.LoggedInUserDao

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
        return Room.databaseBuilder(context = context, klass = AppDatabase::class.java, "app_d.").build()
    }

    @Provides
    fun provideLoggedInDao(database: AppDatabase): LoggedInUserDao = database.loggedInDao
}