package com.example.base.di

import com.example.base.BuildConfig
import com.example.base.api.ApiService
import com.example.base.api.Const
import com.example.base.manager.UserManager
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    @Named("logging")
    fun providerOkhttpLogging(): Interceptor =
        HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG) (HttpLoggingInterceptor.Level.BODY)
            else HttpLoggingInterceptor.Level.NONE
        )

    @Singleton
    @Provides
    @Named("header")
    fun provideHeaderInterceptor(userManager: UserManager): Interceptor =
        Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            if (userManager.isLogged()) {
                requestBuilder.addHeader(Const.HEADER_TOKEN, userManager.getAccessToken())
            }
            requestBuilder.addHeader(Const.HEADER_API_KEY, Const.API_KEY)
            requestBuilder.addHeader(Const.HEADER_AUTHORIZATION, Const.AUTH)
            chain.proceed(requestBuilder.build())
        }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @Named("logging") logging: Interceptor,
        @Named("header") header: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .apply {
                addInterceptor(logging)
                addInterceptor(header)
            }
            .connectTimeout(Const.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Const.WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Const.READ_TIMEOUT, TimeUnit.SECONDS)
            .build()

    @Singleton
    @Provides
    fun providerRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL + BuildConfig.API_VERSION)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi)).build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}