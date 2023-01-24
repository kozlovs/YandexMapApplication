package ru.kozlovss.yandexmapapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.maps.mobile.BuildConfig
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val api = getString(R.string.MAPS_API_KEY)
        MapKitFactory.setApiKey(api)
        MapKitFactory.initialize(this)
        setContentView(R.layout.activity_main)
        mapView = findViewById<MapView?>(R.id.mapview).apply {

            map.move(
                CameraPosition(
                    Point(55.751574, 37.573856), 11.0f, 0.0f, 0.0f
                ),
                Animation(Animation.Type.SMOOTH, 0F),
                null
            )
        }
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }
}