package ru.kozlovss.yandexmapapplication.repository

import androidx.lifecycle.map
import ru.kozlovss.yandexmapapplication.dao.PlaceDao
import ru.kozlovss.yandexmapapplication.dto.Place
import ru.kozlovss.yandexmapapplication.entity.PlaceEntity

class PlaceRepositoryRoomImpl(private val dao: PlaceDao) : PlaceRepository {
    override fun getAll() = dao.getAll().map { list ->
        list.map {it.toDto()}
    }

    override fun removeById(id: Long) {
        dao.removeById(id)
    }

    override fun save(place: Place) {
        dao.save(PlaceEntity.fromDto(place))
    }
}
