package ru.kozlovss.yandexmapapplication.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.kozlovss.yandexmapapplication.db.AppDb
import ru.kozlovss.yandexmapapplication.dto.Place
import ru.kozlovss.yandexmapapplication.repository.PlaceRepository
import ru.kozlovss.yandexmapapplication.repository.PlaceRepositoryRoomImpl

class PlacesViewModel(context: Application) : AndroidViewModel(context) {

    private val emptyPlace = Place(0L, "", 0.0, 0.0)

    private val _place = MutableLiveData(emptyPlace)
    val place: LiveData<Place>
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
        Log.d("MyLog", "setPlace: ${place.value}")
    }

    fun clearPlace() {
        _place.value = emptyPlace
        Log.d("MyLog", "clearPlace: ${_place.value}")
    }
}