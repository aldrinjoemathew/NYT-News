package com.nyt.nytnews.di

import android.content.Context
import android.content.SharedPreferences
import com.nyt.nytnews.NytApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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