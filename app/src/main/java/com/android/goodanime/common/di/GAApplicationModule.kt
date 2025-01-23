package com.android.goodanime.common.di

import android.content.Context
import android.net.ConnectivityManager
import com.android.goodanime.common.api.GARetrofit
import com.android.goodanime.common.connectivity.GANetworkConnectivityStatusProvider
import com.android.goodanime.common.connectivity.GANetworkConnectivityStatusProviderImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * The module that provides the application level dependencies
 */
@Module
@InstallIn(SingletonComponent::class)
object GAApplicationModule {
    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @Provides
    fun provideRetrofit(): GARetrofit = GARetrofit

    @GAIODispatcher
    @Provides
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @GADefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @GAApplicationScope
    @Singleton
    @Provides
    fun providesApplicationScope(
        @GADefaultDispatcher defaultDispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + defaultDispatcher)
}

/**
 * The interface that provides the internet connectivity status
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class GAInternetConnectivityModule {
    @Singleton
    @Binds
    abstract fun bindInternetConnectivityStatusProvider(impl: GANetworkConnectivityStatusProviderImpl): GANetworkConnectivityStatusProvider
}

/**
 * Annotation used to denote a custom scope for objects with application level
 */
@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class GAApplicationScope

/**
 * An annotation used to qualify the default dispatcher for application level
 */
@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class GADefaultDispatcher

/**
 * An annotation used to qualify the I/O (Input/Output) dispatcher for application level
 */
@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class GAIODispatcher