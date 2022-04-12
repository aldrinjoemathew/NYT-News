package com.nyt.nytnews.di

import android.content.Context
import android.content.SharedPreferences
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.nyt.nytnews.BuildConfig
import com.nyt.nytnews.NytApplication
import com.nyt.nytnews.db.NytDatabase
import com.nyt.nytnews.db.dao.UserDao
import com.nyt.nytnews.db.repository.UserRepo
import com.nyt.nytnews.db.repository.UserRepoImpl
import com.nyt.nytnews.network.NytApiService
import com.nyt.nytnews.network.mapper.NewsArticleMapper
import com.nyt.nytnews.network.mapper.NewsResponseMapper
import com.nyt.nytnews.network.repository.NewsRepository
import com.nyt.nytnews.network.repository.NewsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
class AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): NytApplication = app as NytApplication

    @Provides
    fun provideSharedPreferences(application: NytApplication): SharedPreferences =
        application.getSharedPreferences(
            "NytNews",
            Context.MODE_PRIVATE
        )
}

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideUserDao(appDatabase: NytDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): NytDatabase {
        return NytDatabase.buildDatabase(appContext)
    }
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun providesUserRepository(userRepoImpl: UserRepoImpl): UserRepo
}

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
        if (BuildConfig::DEBUG.get()) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        builder.retryOnConnectionFailure(true)
        builder.connectTimeout(3 * TIMEOUT_MILLIS, TIMEOUT_UNIT)
        builder.readTimeout(6 * TIMEOUT_MILLIS, TIMEOUT_UNIT)
        builder.writeTimeout(12 * TIMEOUT_MILLIS, TIMEOUT_UNIT)
        builder.addInterceptor(loggingInterceptor)
        return builder.build()
    }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()

    @Provides
    fun provideConverterFactory(): Converter.Factory {
        val contentType = "application/json".toMediaType()
        return Json{
            ignoreUnknownKeys = true
        }.asConverterFactory(contentType)
    }
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class NetworkRepositoryModule {
    @Binds
    abstract fun providesNewsRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository
}

@Module
@InstallIn(ViewModelComponent::class)
class NetworkMapperModule {
    @Provides
    fun provideNewsResponseMapper(newsArticleMapper: NewsArticleMapper): NewsResponseMapper {
        return NewsResponseMapper(newsArticleMapper)
    }

    @Provides
    fun provideNewsArticleMapper(): NewsArticleMapper {
        return NewsArticleMapper()
    }
}