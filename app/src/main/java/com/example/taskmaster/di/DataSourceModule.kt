package com.example.taskmaster.di

import com.example.taskmaster.data.local.LocalDataSource
import com.example.taskmaster.data.local.LocalDataSourceImpl
import com.example.taskmaster.data.remote.RemoteDataSource
import com.example.taskmaster.data.remote.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindLocalDataSource(
        localDataSourceImpl: LocalDataSourceImpl
    ): LocalDataSource

    @Binds
    abstract fun bindRemoteDataSource(
        remoteDataSourceImpl: RemoteDataSourceImpl
    ): RemoteDataSource
}