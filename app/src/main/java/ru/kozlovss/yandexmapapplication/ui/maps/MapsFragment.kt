package ru.kozlovss.yandexmapapplication.ui.maps

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.yandex.mapkit.Animation
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.ui_view.ViewProvider
import android.Manifest
import android.util.Log
import kotlinx.coroutines.flow.collectLatest
import ru.kozlovss.yandexmapapplication.R
import ru.kozlovss.yandexmapapplication.databinding.FragmentMapsBinding
import ru.kozlovss.yandexmapapplication.databinding.MarkerPlaceBinding
import ru.kozlovss.yandexmapapplication.ui.NewPlaceFragment
import ru.kozlovss.yandexmapapplication.ui.places.PlacesViewModel

class MapsFragment : Fragment() {

    private val viewModel: PlacesViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )
    private var mapView: MapView? = null
    private lateinit var userLocation: UserLocationLayer
    private val listener = object : InputListener {
        override fun onMapTap(map: Map, point: Point) = Unit

        override fun onMapLongTap(map: Map, point: Point) {
            NewPlaceFragment.newInstance(point.latitude, point.longitude)
                .show(childFragmentManager, null)
        }
    }

    private val locationObjectListener = object : UserLocationObjectListener {
        override fun onObjectAdded(view: UserLocationView) = Unit

        override fun onObjectRemoved(view: UserLocationView) = Unit

        override fun onObjectUpdated(view: UserLocationView, event: ObjectEvent) {
            userLocation.cameraPosition()?.target?.let {
                mapView?.map?.move(CameraPosition(it, 10F, 0F, 0F))
            }
            userLocation.setObjectListener(null)
        }
    }

    private val placeTapListener = MapObjectTapListener { mapObject, _ ->
        viewModel.removeById(mapObject.userData as Long)
        true
    }

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            when {
                granted -> {
                    userLocation.isVisible = true
                    userLocation.isHeadingEnabled = false
                    userLocation.cameraPosition()?.target?.also {
                        val map = mapView?.map ?: return@registerForActivityResult
                        val cameraPosition = map.cameraPosition
                        map.move(
                            CameraPosition(
                                it,
                                cameraPosition.zoom,
                                cameraPosition.azimuth,
                                cameraPosition.tilt,
                            )
                        )
                    }
                }
                else -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.need_permission),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        MapKitFactory.initialize(context)
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMapsBinding.inflate(inflater, container, false)

        mapView = binding.mapview.apply {
            userLocation = MapKitFactory.getInstance().createUserLocationLayer(mapWindow)
            if (requireActivity()
                    .checkSelfPermission(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
            ) {
                userLocation.isVisible = true
                userLocation.isHeadingEnabled = false
            }

            map.addInputListener(listener)

            val collection = map.mapObjects.addCollection()
            viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
                viewModel.places.collectLatest { places ->
                    collection.clear()
                    places.forEach { place ->
                        Log.d("MyLog", place.toString())
                        val placeBinding = MarkerPlaceBinding.inflate(layoutInflater)
                        placeBinding.title.text = place.name
                        collection.addPlacemark(
                            Point(place.latitude, place.longitude),
                            ViewProvider(placeBinding.root)
                        ).apply {
                            userData = place.id
                        }
                    }
                }
            }
            collection.addTapListener(placeTapListener)

            // Переход к точке на карте после клика на списке
            val arguments = arguments
            if (arguments != null &&
                arguments.containsKey(LAT_KEY) &&
                arguments.containsKey(LONG_KEY)
            ) {
                val cameraPosition = map.cameraPosition
                map.move(
                    CameraPosition(
                        Point(arguments.getDouble(LAT_KEY), arguments.getDouble(LONG_KEY)),
                        10F,
                        cameraPosition.azimuth,
                        cameraPosition.tilt,
                    )
                )
                arguments.remove(LAT_KEY)
                arguments.remove(LONG_KEY)
            } else {
                // При входе в приложение показываем текущее местоположение
                userLocation.setObjectListener(locationObjectListener)
            }
        }

        binding.plus.setOnClickListener {
            binding.mapview.map.move(
                CameraPosition(
                    binding.mapview.map.cameraPosition.target,
                    binding.mapview.map.cameraPosition.zoom + 1, 0.0f, 0.0f
                ),
                Animation(Animation.Type.SMOOTH, 1F),
                null
            )
        }

        binding.minus.setOnClickListener {
            binding.mapview.map.move(
                CameraPosition(
                    binding.mapview.map.cameraPosition.target,
                    binding.mapview.map.cameraPosition.zoom - 1, 0.0f, 0.0f
                ),
                Animation(Animation.Type.SMOOTH, 1F),
                null,
            )
        }

        binding.location.setOnClickListener {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        mapView = null
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView?.onStart()
        mapView?.apply {
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
        mapView?.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    companion object {
        const val LAT_KEY = "LAT_KEY"
        const val LONG_KEY = "LONG_KEY"
    }
}