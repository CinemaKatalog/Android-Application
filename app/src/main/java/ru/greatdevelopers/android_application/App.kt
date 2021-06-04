package ru.greatdevelopers.android_application

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.greatdevelopers.android_application.di.*

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(viewModelModule, databaseModule, repositoryModule, appModule, netModule, apiModule)
        }
    }
}