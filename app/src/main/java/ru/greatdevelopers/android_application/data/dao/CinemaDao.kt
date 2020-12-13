package ru.greatdevelopers.android_application.data.dao

import androidx.room.*
import ru.greatdevelopers.android_application.data.model.Cinema
import ru.greatdevelopers.android_application.data.model.Favourite

@Dao
interface CinemaDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCinema(cinema: Cinema)

    @Update
    suspend fun updateCinema(cinema: Cinema)

    @Delete
    suspend fun deleteCinema(cinema: Cinema)

    @Query("SELECT * FROM Cinema WHERE site_url == :url")
    suspend fun getCinemaByUrl(url: String): Cinema

    @Query("SELECT * FROM Cinema WHERE cinema_name == :name")
    suspend fun getCinemaByName(name: String): Cinema?

    @Query("SELECT * FROM Cinema")
    suspend fun getAllCinema(): List<Cinema>
}