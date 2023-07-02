package id.myone.my_news

import android.app.Application
import id.myone.my_news.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaseApplication)
            workManagerFactory()
            modules(appNetworkModule, appDatabaseModule, viewModelModule, appUtilityModule, workerModule)
        }
    }
}