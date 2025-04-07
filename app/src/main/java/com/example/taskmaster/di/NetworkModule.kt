package com.example.taskmaster.di

import androidx.multidex.BuildConfig
import com.example.taskmaster.data.local.preferences.EncryptedPreferenceManager
import com.example.taskmaster.data.local.preferences.TokenProvider
import com.example.taskmaster.data.remote.api.service.AuthService
import com.example.taskmaster.data.remote.api.service.client.AuthInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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
                if (BuildConfig.DEBUG) {
                    val logger = HttpLoggingInterceptor()
                    logger.setLevel(HttpLoggingInterceptor.Level.BODY)
                    client.addInterceptor(logger)
                }
            }
            .build()

    // Provide Moshi instance
    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    // Provide Retrofit instance
    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL) // Replace BASE_URL with your actual base URL
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    // Provide AuthService instance
    @Singleton
    @Provides
    fun provideAuthService(retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)
}