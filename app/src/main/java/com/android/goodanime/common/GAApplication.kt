package com.android.goodanime.common

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * The Application class for the application. This class is annotated with
 * [HiltAndroidApp] to enable Hilt for dependency injection in the application.
 */
@HiltAndroidApp
class GAApplication : Application() {

    companion object {
        private lateinit var instance: GAApplication

        /**
         * Retrieves the singleton instance of the [GAApplication].
         *
         * @return The application instance.
         */
        fun getInstance(): GAApplication = instance
    }

    /**
     * Called when the application is first created. Invokes the [initInstance] method to initialize
     * the singleton instance.
     */
    override fun onCreate() {
        super.onCreate()
        initInstance()
    }

    /**
     * Initializes the singleton instance for the current application.
     */
    private fun initInstance() {
        instance = this
    }
}