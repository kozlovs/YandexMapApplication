package ru.kozlovss.yandexmapapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.kozlovss.yandexmapapplication.db.AppDb
import ru.kozlovss.yandexmapapplication.dto.Place
import ru.kozlovss.yandexmapapplication.repository.PlaceRepository
import ru.kozlovss.yandexmapapplication.repository.PlaceRepositoryRoomImpl

class PlacesViewModel(context: Application) : AndroidViewModel(context) {

    private val _place = MutableStateFlow<Place?>(null)
    val place: StateFlow<Place?>
        get() = _place

    private val repository: PlaceRepository = PlaceRepositoryRoomImpl(
        AppDb.getInstance(context).placeDao()
    )

    val places = repository.getAll()

    fun insert(place: Place) {
        repository.insert(place)
    }

    fun removeById(id: Long) {
        repository.removeById(id)
    }

    fun setPlace(targetPlace: Place) {
        _place.value = targetPlace
    }

    fun clearPlace() {
        _place.value = null
    }
}