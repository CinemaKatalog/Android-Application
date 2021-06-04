package ru.greatdevelopers.android_application.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.greatdevelopers.android_application.data.model.FilmCinema
import ru.greatdevelopers.android_application.data.model.User
import ru.greatdevelopers.android_application.ui.filmscreen.CinemaListItem

@Dao
interface FilmCinemaDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFilmCinema(filmCinema: FilmCinema)

    @Update
    suspend fun updateFilmCinema(filmCinema: FilmCinema)

    @Delete
    suspend fun deleteFilmCinema(filmCinema: FilmCinema)

    @Query("SELECT * FROM FilmCinema WHERE film_id == :filmId")
    suspend fun getFilmCinemaById(filmId: Int): List<FilmCinema>

    @Query("SELECT * FROM FilmCinema WHERE film_id == :filmId AND site_url == :siteUrl")
    suspend fun getFilmCinemaByIds(filmId: Long, siteUrl: String): FilmCinema?

    @Query("SELECT Cinema.cinema_name, FilmCinema.price, FilmCinema.rating, FilmCinema.page_url, FilmCinema.film_id, FilmCinema.site_url " +
            "FROM FilmCinema, Cinema " +
            "WHERE FilmCinema.site_url == Cinema.site_url AND FilmCinema.film_id == :filmId")
    suspend fun getFilmCinemaWithName(filmId: Long): List<CinemaListItem>
}

