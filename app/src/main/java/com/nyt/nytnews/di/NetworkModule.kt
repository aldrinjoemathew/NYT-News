package com.nyt.nytnews.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.nyt.nytnews.BuildConfig
import com.nyt.nytnews.data.network.NytApiService
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
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    private val TIMEOUT_UNIT = TimeUnit.MILLISECONDS
    private val TIMEOUT_MILLIS: Long = 10000

    @Provides
    fun provideApi(retrofit: Retrofit): NytApiService = retrofit.create(NytApiService::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, converter: Converter.Factory): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converter)
            .build()

    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.retryOnConnectionFailure(true)
        builder.connectTimeout(3 * TIMEOUT_MILLIS, TIMEOUT_UNIT)
        builder.readTimeout(6 * TIMEOUT_MILLIS, TIMEOUT_UNIT)
        builder.writeTimeout(12 * TIMEOUT_MILLIS, TIMEOUT_UNIT)
        builder.addInterceptor(loggingInterceptor)
        return builder.build()
    }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig::DEBUG.get()) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return loggingInterceptor
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    fun provideConverterFactory(json: Json): Converter.Factory {
        val contentType = "application/json".toMediaType()
        return json.asConverterFactory(contentType)
    }

    @Provides
    fun provideJsonSerializer(): Json {
        return Json {
            ignoreUnknownKeys = true
        }
    }
}