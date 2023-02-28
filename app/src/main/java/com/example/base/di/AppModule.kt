package com.example.base.di

import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import com.example.base.manager.PrefManager
import com.example.base.manager.UserManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideUserManage(prefManager: PrefManager): UserManager = UserManager(prefManager)

    @Singleton
    @Provides
    fun providerResources(@ApplicationContext context: Context): Resources = context.resources

    @Singleton
    @Provides
    fun provideAssetManager(@ApplicationContext context: Context): AssetManager = context.assets

    @Singleton
    @Provides
    fun provideSharedPrefs(@ApplicationContext context: Context, moshi: Moshi): PrefManager =
        PrefManager(context, moshi)

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
}