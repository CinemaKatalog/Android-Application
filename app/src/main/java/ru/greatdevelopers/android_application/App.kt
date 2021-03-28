package ru.greatdevelopers.android_application

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.greatdevelopers.android_application.di.appModule
import ru.greatdevelopers.android_application.di.databaseModule
import ru.greatdevelopers.android_application.di.repositoryModule
import ru.greatdevelopers.android_application.di.viewModelModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(viewModelModule, databaseModule, repositoryModule, appModule)
        }
    }
}