package com.cloudesire.microsoftpartnercenter.services

import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


class ServiceGenerator(authToken: String? = null)
{
    private var apiBaseUrl = "https://api.partnercenter.microsoft.com"
    private var httpClient: OkHttpClient
    private var moshi: Moshi
    private var builder: Retrofit.Builder

    init
    {
        val interceptor = { chain: Interceptor.Chain ->
            val request = chain.request().newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", "Bearer $authToken")
                    .build()
            chain.proceed(request)
        }

        httpClient = OkHttpClient
                .Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build()

        moshi = Moshi
                .Builder()
                .build()

        builder = Retrofit
                .Builder()
                .baseUrl(apiBaseUrl)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
    }

    fun changeApiBaseUrl(newApiBaseUrl: String): ServiceGenerator
    {
        apiBaseUrl = newApiBaseUrl
        builder = Retrofit
                .Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(apiBaseUrl)

        return this
    }

    fun <S> createService(serviceClass: Class<S>): S
    {
        val retrofit = builder
                .client(httpClient)
                .build()

        return retrofit.create(serviceClass)
    }
}
