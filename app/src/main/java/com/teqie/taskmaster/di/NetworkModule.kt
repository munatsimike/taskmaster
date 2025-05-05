package com.teqie.taskmaster.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.teqie.taskmaster.data.local.preferences.EncryptedPreferenceManager
import com.teqie.taskmaster.data.local.preferences.TokenProvider
import com.teqie.taskmaster.data.remote.api.service.AuthService
import com.teqie.taskmaster.data.remote.api.service.BudgetPhaseService
import com.teqie.taskmaster.data.remote.api.service.DashboardService
import com.teqie.taskmaster.data.remote.api.service.FileManagerService
import com.teqie.taskmaster.data.remote.api.service.ProjectService
import com.teqie.taskmaster.data.remote.api.service.TeamService
import com.teqie.taskmaster.data.remote.api.service.client.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * NetworkModule provides network-related dependencies for the application,
 * including OkHttpClient, Moshi, and Retrofit instances, which are used
 * for making network requests and handling JSON data.
 */


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://urchin-app-rcbfh.ondigitalocean.app/"

    // Provide the TokenProvider instead of a static AccessToken
    @Provides
    @Singleton
    fun provideTokenProvider(encryptedPreferenceManager: EncryptedPreferenceManager): TokenProvider {
        return TokenProvider(encryptedPreferenceManager)
    }

    // Provide AuthInterceptor using TokenProvider to fetch the latest token
    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenProvider: TokenProvider): AuthInterceptor {
        return AuthInterceptor(tokenProvider)
    }

    // Provide OkHttpClient with AuthInterceptor
    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .also { client ->
                if (com.teqie.taskmaster.BuildConfig.DEBUG) {
                    val logger = HttpLoggingInterceptor()
                    logger.setLevel(HttpLoggingInterceptor.Level.BODY)
                    client.addInterceptor(logger)
                }
            }
            .build()

    @OptIn(ExperimentalSerializationApi::class)
    @Singleton
    @Provides
    fun provideKotlinxSerializationConverterFactory(): Converter.Factory {
        val json = Json {
            ignoreUnknownKeys = true
            explicitNulls = false
            isLenient = true
        }

        return json.asConverterFactory("application/json".toMediaType())
    }

    // Provide Retrofit instance

    @Singleton
    @Provides
    fun provideRetrofit( converterFactory: Converter.Factory, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL) // Replace BASE_URL with your actual base URL
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()

    // Provide AuthService instance
    @Singleton
    @Provides
    fun provideAuthService(retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    // Provide ProjectService instance
    @Singleton
    @Provides
    fun provideProjectService(retrofit: Retrofit): ProjectService =
        retrofit.create(ProjectService::class.java)

    // Provide ProjectService instance
    @Singleton
    @Provides
    fun provideDashboardService(retrofit: Retrofit): DashboardService =
        retrofit.create(DashboardService::class.java)

    @Singleton
    @Provides
    fun provideBudgetPhaseService(retrofit: Retrofit): BudgetPhaseService =
        retrofit.create(BudgetPhaseService::class.java)

    @Singleton
    @Provides
    fun provideTeamsService(retrofit: Retrofit): TeamService =
        retrofit.create(TeamService::class.java)

    @UploadClient
    @Singleton
    @Provides
    fun provideFileManagementService(retrofit: Retrofit): FileManagerService =
        retrofit.create(FileManagerService::class.java)
}

