package ru.kozlovss.yandexmapapplication.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.kozlovss.yandexmapapplication.entity.PlaceEntity

@Dao
interface PlaceDao {
    @Query("SELECT * FROM PlaceEntity ORDER BY id DESC")
    fun getAll(): Flow<List<PlaceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(place: PlaceEntity)

    @Query("DELETE FROM PlaceEntity WHERE id = :id")
    fun removeById(id: Long)
}