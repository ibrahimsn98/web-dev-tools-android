package me.ibrahimsn.wdevtools

import android.app.Application
import me.ibrahimsn.core.domain.source.LocalDataSource
import me.ibrahimsn.core.presentation.model.ConsentManager
import me.ibrahimsn.wdevtools.di.appDataModule
import me.ibrahimsn.wdevtools.di.appDomainModule
import me.ibrahimsn.wdevtools.di.appViewModelModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    private val preferences: LocalDataSource.Preferences by inject()

    override fun onCreate() {
        super.onCreate()
        startInject()
        onInject()
    }

    private fun startInject() {
        val appModules = appDataModule + appDomainModule + appViewModelModule
        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(appModules)
        }
    }

    private fun onInject() {
        ConsentManager.setupPreferences(preferences)
    }
}
