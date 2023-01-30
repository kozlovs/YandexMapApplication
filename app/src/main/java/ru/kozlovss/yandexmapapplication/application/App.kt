package ru.kozlovss.yandexmapapplication.application

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import ru.kozlovss.yandexmapapplication.R

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(getString(R.string.MAPS_API_KEY))
    }
}