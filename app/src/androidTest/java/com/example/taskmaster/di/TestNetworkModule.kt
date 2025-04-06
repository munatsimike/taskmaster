package com.example.taskmaster.di

import androidx.test.espresso.core.internal.deps.dagger.Module
import com.example.taskmaster.data.remote.api.service.AuthService
import com.squareup.moshi.Moshi
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
@Module
object TestNetworkModule {

    @Singleton
    @Provides
    fun providesMockWebServer(): MockWebServer = MockWebServer()

    @Provides
    @Named("testMovieService")
    fun providesTestAuthService(moshi: Moshi, mockWebServer: MockWebServer): AuthService {
        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        return retrofit.create(AuthService::class.java)
    }
}
