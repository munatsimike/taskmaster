package com.teqie.taskmaster.data.remote.api.service.client

import com.teqie.taskmaster.data.local.preferences.TokenProvider
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

/**
 * The api key interceptor is responding for appending the api key to HTTP requests.
 * The class takes the api key as the argument add appends is as a query parameter to the original URL.
 *
 */
class AuthInterceptor @Inject constructor(private val tokenProvider: TokenProvider) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()

        // Synchronously fetch the latest access token
        val accessToken = runBlocking { tokenProvider.getAccessToken().value }

        // Modify the request to add the Authorization header with the token
        val requestWithToken: Request = originalRequest.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()

        return chain.proceed(requestWithToken)
    }
}