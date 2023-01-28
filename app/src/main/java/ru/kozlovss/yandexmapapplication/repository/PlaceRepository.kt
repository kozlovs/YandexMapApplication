package ru.kozlovss.yandexmapapplication.repository

import androidx.lifecycle.LiveData
import ru.kozlovss.yandexmapapplication.dto.Place

interface PlaceRepository {
    fun getAll(): LiveData<List<Place>>
    fun removeById(id: Long)
    fun save(place: Place)
}