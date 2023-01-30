package ru.kozlovss.yandexmapapplication.repository

import kotlinx.coroutines.flow.Flow
import ru.kozlovss.yandexmapapplication.dto.Place

interface PlaceRepository {
    fun getAll(): Flow<List<Place>>
    fun removeById(id: Long)
    fun insert(place: Place)
}