package ru.kozlovss.yandexmapapplication.ui.places

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ru.kozlovss.yandexmapapplication.db.AppDb
import ru.kozlovss.yandexmapapplication.dto.Place
import ru.kozlovss.yandexmapapplication.repository.PlaceRepository
import ru.kozlovss.yandexmapapplication.repository.PlaceRepositoryRoomImpl

private val empty = Place(0L, "", -1F, -1F)

class PlacesViewModel(application: Application): AndroidViewModel(application) {


    private val repository: PlaceRepository = PlaceRepositoryRoomImpl(
        AppDb.getInstance(application).placeDao()
    )

    fun edit(place: Place) {
        repository.save(place)
    }

    fun removeById(id: Long) {
        repository.removeById(id)
    }
}