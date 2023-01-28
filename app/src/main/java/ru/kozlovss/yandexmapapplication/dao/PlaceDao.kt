package ru.kozlovss.yandexmapapplication.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.kozlovss.yandexmapapplication.entity.PlaceEntity

@Dao
interface PlaceDao {
    @Query("SELECT * FROM PlaceEntity ORDER BY id DESC")
    fun getAll(): LiveData<List<PlaceEntity>>

    @Insert
    fun insert(place: PlaceEntity)

    @Query("UPDATE PlaceEntity SET name = :name, latitude = :latitude, longitude = :longitude WHERE id = :id")
    fun updateItemById(id: Long, name: String, latitude: Float, longitude: Float)

    fun save(place: PlaceEntity) =
        if (place.id == 0L) insert(place) else updateItemById(
            place.id,
            place.name,
            place.latitude,
            place.longitude
        )

    @Query("DELETE FROM PlaceEntity WHERE id = :id")
    fun removeById(id: Long)
}