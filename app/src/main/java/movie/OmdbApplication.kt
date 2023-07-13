package movie

import android.app.Application
import cache.cacheModule
import data.dataModule
import domain.domainModule
import movie.appmodule.appModule
import presentation.presentationModule
import remote.remoteModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
Custom Application class for the OMDB (Open Movie Database) application.
This class extends the base Application class and is responsible for initializing
and configuring the application at startup.
*/
internal class OmdbApplication: Application() {
/*
Called when the application is first created.
Initializes the Koin dependency injection framework and sets up the necessary modules
for the application.
*/
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@OmdbApplication)
            androidLogger()
            modules(
                appModule, // Module for application-level dependencies
                cacheModule, // Module for caching-related dependencies
                domainModule, // Module for domain-related dependencies
                dataModule, // Module for data-related dependencies
                presentationModule, // Module for presentation-related dependencies
                remoteModule // Module for remote-related dependencies
            )
        }
    }
}
