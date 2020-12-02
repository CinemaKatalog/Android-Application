package ru.greatdevelopers.android_application.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.greatdevelopers.android_application.data.model.Film

@Dao
interface FilmDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFilm(film: Film)

    @Update
    suspend fun updateFilm(film: Film)

    @Delete
    suspend fun deleteFilm(film: Film)

    @Query("SELECT * FROM Film ORDER BY film_name ASC")
    suspend fun getFilmAll(): List<Film>

    @Query("SELECT * FROM Film WHERE film_name == :name")
    suspend fun getFilmByName(name: String): List<Film>

    @Query("SELECT * FROM Film WHERE film_id == :id")
    suspend fun getFilmById(id: Int): List<Film>

    @Query("SELECT * FROM Film WHERE genre == :genre AND country == :country AND year >= :minYear AND year <= :maxYear AND rating >= :minRating AND rating <= :maxRating")
    suspend fun getFilmByAllParams(
        genre: String,
        country: String,
        maxYear: Int,
        minYear: Int,
        minRating: Float,
        maxRating: Float
    ): List<Film>

    @Query("SELECT * FROM Film WHERE genre == :genre")
    suspend fun getFilmByGenre(genre: String): List<Film>

    @Query("SELECT * FROM Film WHERE country == :country")
    suspend fun getFilmByCountry(country: String): List<Film>

    @Query("SELECT * FROM Film WHERE rating == :rating")
    suspend fun getFilmByRating(rating: String): List<Film>

    @Query("SELECT * FROM Film WHERE Film.film_id == (SELECT film_id FROM Favourite WHERE Favourite.user_id == :userId) ORDER BY film_name ASC")
    suspend fun getFavouriteFilms(userId: Int): List<Film>
}
