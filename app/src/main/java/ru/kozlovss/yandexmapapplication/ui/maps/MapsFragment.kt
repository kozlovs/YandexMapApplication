package ru.kozlovss.yandexmapapplication.ui.maps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import ru.kozlovss.yandexmapapplication.R

class MapsFragment : Fragment() {

    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        MapKitFactory.setApiKey(getString(R.string.MAPS_API_KEY))
        MapKitFactory.initialize(context)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mapView = view.findViewById(R.id.mapview)
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
        mapView.apply {
            map.move(
                CameraPosition(
                    Point(55.751574, 37.573856), 11.0f, 0.0f, 0.0f
                ),
                Animation(Animation.Type.SMOOTH, 0F),
                null
            )
        }
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }
}