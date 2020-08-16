package co.numeriq.articles.core

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import co.numeriq.articles.di.apiModule
import co.numeriq.articles.di.networkModule
import co.numeriq.articles.di.repoModule
import co.numeriq.articles.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CoreApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            printLogger() // Koin Logger
            androidContext(this@CoreApplication)
            modules(listOf(apiModule, viewModelModule, networkModule, repoModule))
        }
    }

    companion object {
        lateinit var instance: CoreApplication

        fun isNetworkAvailable(): Boolean {
            val cm = instance.applicationContext
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.activeNetworkInfo?.isConnected ?: false
        }
    }

}