package app.junsu.weather

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.koinApplication

class WeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        koinApplication {
            androidContext(this@WeatherApp)
            modules()
        }
    }
}
