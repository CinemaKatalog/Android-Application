package ru.greatdevelopers.android_application.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.greatdevelopers.android_application.data.model.Film
import ru.greatdevelopers.android_application.ui.filmscreen.CinemaListItem
import ru.greatdevelopers.android_application.ui.mainscreen.adapters.FilmListItem

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

    @Query("SELECT * FROM Film WHERE film_name LIKE '%' || :name || '%' ORDER BY film_name ASC")
    suspend fun getFilmByName(name: String): List<Film>

    @Query("SELECT Film.film_id, Film.film_name, Genre.genre_name, Film.poster, Film.rating " +
            "FROM Film, Genre WHERE Film.genre == Genre.genre_id AND Film.film_name LIKE '%' || :name || '%' ORDER BY Film.film_name ASC")
    suspend fun getFilmByNameWithExtra(name: String): List<FilmListItem>

    @Query("SELECT * FROM Film WHERE film_id == :id")
    suspend fun getFilmById(id: Long): Film?

    @Query(
        "SELECT Film.film_id, Film.film_name, Genre.genre_name, Film.poster, Film.rating " +
                "FROM Film, Genre " +
                "WHERE Film.genre == Genre.genre_id AND Film.genre == :genre AND Film.country == :country AND " +
                "year >= :minYear AND year <= :maxYear AND rating >= :minRating AND rating <= :maxRating ORDER BY Film.film_name ASC"
    )
    suspend fun getFilmByAllParams(
        genre: Long,
        country: Long,
        minYear: Int,
        maxYear: Int,
        minRating: Float,
        maxRating: Float
    ): List<FilmListItem>

    @Query(
        "SELECT Film.film_id, Film.film_name, Genre.genre_name, Film.poster, Film.rating " +
                "FROM Film, Genre " +
                "WHERE Film.genre == Genre.genre_id AND Film.genre == :genre AND year >= :minYear AND year <= :maxYear AND rating >= :minRating AND rating <= :maxRating" +
                " ORDER BY Film.film_name ASC"
    )
    suspend fun getFilmByParamsWC(
        genre: Long,
        minYear: Int,
        maxYear: Int,
        minRating: Float,
        maxRating: Float
    ): List<FilmListItem>

    @Query(
        "SELECT Film.film_id, Film.film_name, Genre.genre_name, Film.poster, Film.rating " +
                "FROM Film, Genre " +
                "WHERE Film.genre == Genre.genre_id AND Film.country == :country " +
                "AND year >= :minYear AND year <= :maxYear AND rating >= :minRating AND rating <= :maxRating" +
                " ORDER BY Film.film_name ASC"
    )
    suspend fun getFilmByParamsWG(
        country: Long,
        minYear: Int,
        maxYear: Int,
        minRating: Float,
        maxRating: Float
    ): List<FilmListItem>

    @Query(
        "SELECT Film.film_id, Film.film_name, Genre.genre_name, Film.poster, Film.rating " +
                "FROM Film, Genre " +
                "WHERE Film.genre == Genre.genre_id AND year >= :minYear AND year <= :maxYear AND rating >= :minRating AND rating <= :maxRating" +
                " ORDER BY Film.film_name ASC"
    )
    suspend fun getFilmByParamsWGC(
        minYear: Int,
        maxYear: Int,
        minRating: Float,
        maxRating: Float
    ): List<FilmListItem>

    @Query("SELECT * FROM Film WHERE genre == :genre")
    suspend fun getFilmByGenre(genre: String): List<Film>

    @Query("SELECT * FROM Film WHERE country == :country")
    suspend fun getFilmByCountry(country: String): List<Film>

    @Query("SELECT * FROM Film WHERE rating == :rating")
    suspend fun getFilmByRating(rating: String): List<Film>

    @Query("SELECT * FROM Film WHERE Film.film_id == (SELECT film_id FROM Favourite WHERE Favourite.user_id == :userId) ORDER BY film_name ASC")
    suspend fun getFavouriteFilms(userId: Int): List<Film>

    @Query(
        "SELECT Film.film_id, Film.film_name, Genre.genre_name, Film.poster, Film.rating " +
                "FROM Film, Genre " +
                "WHERE Film.genre == Genre.genre_id ORDER BY Film.film_name ASC"
    )
    suspend fun getFilmWithExtra(): List<FilmListItem>

    @Query(
        "SELECT Film.film_id, Film.film_name, Genre.genre_name, Film.poster, Film.rating " +
                "FROM Film INNER JOIN Genre ON Film.genre == Genre.genre_id " +
                "INNER JOIN Favourite ON Film.film_id == Favourite.film_id " +
                "WHERE Favourite.user_id == :userId ORDER BY Film.film_name ASC"
    )
    suspend fun getFavourFilmWithExtra(userId: Int): List<FilmListItem>
}

