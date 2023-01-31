package ru.kozlovss.yandexmapapplication.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.kozlovss.yandexmapapplication.dto.Place

@Entity
data class PlaceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val latitude: Double,
    val longitude: Double
) {
    companion object {
        fun fromDto(place: Place) = with(place) {
            PlaceEntity(id, name, latitude, longitude)
        }
    }

    fun toDto() = Place(id, name, latitude, longitude)
}
