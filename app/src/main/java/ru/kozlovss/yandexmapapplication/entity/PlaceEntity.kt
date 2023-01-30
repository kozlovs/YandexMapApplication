package ru.kozlovss.yandexmapapplication.entity

import androidx.lifecycle.LiveData
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.kozlovss.yandexmapapplication.dto.Place

@Entity
data class PlaceEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val latitude: Float,
    val longitude: Float
) {
    companion object {
        fun fromDto(place: Place) = with(place) {
            PlaceEntity(id, name, latitude, longitude)
        }
    }

    fun toDto() = Place(id, name, latitude, longitude)
}
