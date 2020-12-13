package ru.greatdevelopers.android_application.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.greatdevelopers.android_application.data.model.Favourite
import ru.greatdevelopers.android_application.data.model.User

@Dao
interface FavouriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavourite(favourite: Favourite)

    @Update
    suspend fun updateFavourite(favourite: Favourite)

    @Delete
    suspend fun deleteFavourite(favourite: Favourite)

    @Query("SELECT * FROM Favourite WHERE user_id = :userId AND film_id = :filmId")
    suspend fun getFavouriteById(filmId: Int, userId: Int): Favourite?
}