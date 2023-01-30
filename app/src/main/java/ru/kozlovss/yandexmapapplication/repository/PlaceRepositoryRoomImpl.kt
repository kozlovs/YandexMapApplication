package ru.kozlovss.yandexmapapplication.repository

import kotlinx.coroutines.flow.map
import ru.kozlovss.yandexmapapplication.dao.PlaceDao
import ru.kozlovss.yandexmapapplication.dto.Place
import ru.kozlovss.yandexmapapplication.entity.PlaceEntity

class PlaceRepositoryRoomImpl(private val dao: PlaceDao) : PlaceRepository {
    override fun getAll() = dao
    .getAll()
    .map { it.map(PlaceEntity::toDto) }

    override fun removeById(id: Long) {
        dao.removeById(id)
    }

    override fun insert(place: Place) {
        dao.insert(PlaceEntity.fromDto(place))
    }
}
