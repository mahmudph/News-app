package id.myone.paging_3_example

import android.app.Application
import id.myone.paging_3_example.di.appDatabaseModule
import id.myone.paging_3_example.di.appNetworkModule
import id.myone.paging_3_example.di.appUtilityModule
import id.myone.paging_3_example.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaseApplication)
            modules(appNetworkModule, appDatabaseModule, viewModelModule, appUtilityModule)
        }
    }
}