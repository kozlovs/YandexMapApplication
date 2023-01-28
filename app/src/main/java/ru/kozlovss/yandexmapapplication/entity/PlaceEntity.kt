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
        fun fromDto(place: Place) = PlaceEntity(
            place.id, place.name, place.latitude, place.longitude
        )
    }

    fun toDto() = Place(this.id, this.name, this.latitude, this.longitude)
}
