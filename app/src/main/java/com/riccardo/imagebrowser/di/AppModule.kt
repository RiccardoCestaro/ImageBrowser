package com.riccardo.imagebrowser.di

import com.google.gson.GsonBuilder
import com.riccardo.imagebrowser.BuildConfig
import com.riccardo.imagebrowser.data.repository.SearchRepositoryImpl
import com.riccardo.imagebrowser.data.service.SearchApiService
import com.riccardo.imagebrowser.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                val loggingInterceptor =
                    HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(loggingInterceptor)
            }
            addInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .addHeader(
                        "Accept-Version",
                        "v1"
                    ) // set accept version to v1 as described in the Unsplash documentation
                    .addHeader(
                        "Authorization",
                        "Client-ID ${BuildConfig.ACCESS_KEY}"
                    ) // set the access key
                    .build()
                chain.proceed(request)
            }
        }.build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.unsplash.com/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideSearchApiService(
        retrofit: Retrofit,
    ): SearchApiService {
        return retrofit.create(SearchApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideSearchRepository(
        searchApiService: SearchApiService
    ): SearchRepository =
        SearchRepositoryImpl(searchApiService)

}