package ru.greatdevelopers.android_application.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.greatdevelopers.android_application.data.model.FilmCinema
import ru.greatdevelopers.android_application.data.model.User

@Dao
interface FilmCinemaDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertFilmCinema(filmCinema: FilmCinema)

    @Update
    suspend fun updateFilmCinema(filmCinema: FilmCinema)

    @Delete
    suspend fun deleteFilmCinema(filmCinema: FilmCinema)

    @Query("SELECT * FROM FilmCinema WHERE film_id == :filmId")
    suspend fun getFilmCinemaByLogin(filmId: Int): List<FilmCinema>

}