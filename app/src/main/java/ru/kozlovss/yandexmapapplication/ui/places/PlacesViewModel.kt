package ru.kozlovss.yandexmapapplication.ui.places

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ru.kozlovss.yandexmapapplication.db.AppDb
import ru.kozlovss.yandexmapapplication.dto.Place
import ru.kozlovss.yandexmapapplication.repository.PlaceRepository
import ru.kozlovss.yandexmapapplication.repository.PlaceRepositoryRoomImpl

class PlacesViewModel(context: Application): AndroidViewModel(context) {

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
}