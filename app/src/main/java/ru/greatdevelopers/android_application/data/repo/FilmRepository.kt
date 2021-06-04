package ru.greatdevelopers.android_application.data.repo

import ru.greatdevelopers.android_application.data.dao.CountryDao
import ru.greatdevelopers.android_application.data.dao.FavouriteDao
import ru.greatdevelopers.android_application.data.dao.FilmDao
import ru.greatdevelopers.android_application.data.dao.GenreDao
import ru.greatdevelopers.android_application.data.model.Country
import ru.greatdevelopers.android_application.data.model.Favourite
import ru.greatdevelopers.android_application.data.model.Film
import ru.greatdevelopers.android_application.data.model.Genre
import ru.greatdevelopers.android_application.data.reqmodel.FavouriteRequest
import ru.greatdevelopers.android_application.data.reqmodel.SearchParams
import ru.greatdevelopers.android_application.network.FilmApiInterface
import ru.greatdevelopers.android_application.ui.mainscreen.adapters.FilmListItem

class FilmRepository(
    private val filmDao: FilmDao,
    private val favouriteDao: FavouriteDao,
    private val genreDao: GenreDao,
    private val countryDao: CountryDao,
    private val filmApiInterface: FilmApiInterface
) {

    suspend fun getAllGenres(): List<Genre> {
        //return genreDao.getAllGenre()
        return filmApiInterface.getAllGenre()
    }

    suspend fun getAllCountries(): List<Country> {
        //return countryDao.getAllCountry()
        return filmApiInterface.getAllCountry()
    }

    suspend fun getGenreById(id: Long): Genre? {
        //return genreDao.getGenreById(id)
        return filmApiInterface.getGenreById(id)
    }

    suspend fun getCountryById(id: Long): Country? {
        //return countryDao.getCountryById(id)
        return filmApiInterface.getCountryById(id)
    }

    suspend fun getAllFilms(): List<Film> {
        return filmDao.getFilmAll()
    }

    suspend fun getFilmById(filmId: Long): Film? {
        //return filmDao.getFilmById(filmId)
        val film = filmApiInterface.getFilmById(filmId)
        return if (film != null) {
            Film(
                film.id, film.name,
                film.genre.id,
                film.country.id,
                film.producer,
                film.description,
                film.poster,
                film.year,
                film.rating
            )
        } else {
            null
        }

    }

    suspend fun getFilmByName(filmName: String): List<Film> {
        return filmDao.getFilmByName(filmName)
    }

    suspend fun getFilmByNameWithExtra(filmName: String): List<FilmListItem> {
        //return filmDao.getFilmByNameWithExtra(filmName)
        return filmApiInterface.getFilmByNameWithExtra(filmName)
    }

    suspend fun getFilmByParameters(
        genre: Long?,
        country: Long?,
        minYear: Int,
        maxYear: Int,
        minRating: Float,
        maxRating: Float
    ): List<FilmListItem> {
        /*return if (genre == null && country == null) {
            filmDao.getFilmByParamsWGC(maxYear, minYear, minRating, maxRating)
        } else if (genre == null) {
            filmDao.getFilmByParamsWG(country!!, maxYear, minYear, minRating, maxRating)
        } else if (country == null) {
            filmDao.getFilmByParamsWC(genre, maxYear, minYear, minRating, maxRating)
        } else {
            filmDao.getFilmByAllParams(genre, country, maxYear, minYear, minRating, maxRating)
        }*/

        return filmApiInterface.getFilmByParameters(
            SearchParams(
                genre,
                country,
                minYear,
                maxYear,
                minRating,
                maxRating
            )
        )
    }

    suspend fun getFavouriteFilms(userId: Int): List<Film> {
        return filmDao.getFavouriteFilms(userId)
    }

    suspend fun getFavouriteFilmsWithExtra(userId: Long): List<FilmListItem> {
        return filmApiInterface.getFavourFilmWithExtra(userId)
        //return filmDao.getFavourFilmWithExtra(userId)
    }

    suspend fun getFilmsWithExtra(): List<FilmListItem> {
        //return filmDao.getFilmWithExtra()
        return filmApiInterface.getFilmWithExtra()
    }

    suspend fun getFavouriteById(filmId: Long, userId: Long): Favourite? {
        //return favouriteDao.getFavouriteById(filmId, userId)
        val film = filmApiInterface.getFavouriteById(FavouriteRequest(userId = userId, filmId = filmId))
        return if (film != null) {
            Favourite(
                filmId = film.responseFilm.id,
                userId = film.responseUser.id
            )
        } else {
            null
        }
    }
    suspend fun insertFavourite(favourite: Favourite) {
        filmApiInterface.insertFavourite(favourite)
        //favouriteDao.insertFavourite(favourite)
    }


    suspend fun delFavourite(favourite: Favourite) {
        //favouriteDao.deleteFavourite(favourite)
        filmApiInterface.deleteFavourite(favourite)
    }

    suspend fun insertFilm(film: Film) {
        filmApiInterface.insertFilm(film)
        //filmDao.insertFilm(film)
    }

    suspend fun insertGenre(genre: Genre) {
        //genreDao.insertGenre(genre)
        filmApiInterface.insertGenre(genre)
    }

    suspend fun insertCountry(country: Country) {
        //countryDao.insertCountry(country)
        filmApiInterface.insertCountry(country)
    }

    suspend fun updateGenre(genre: Genre) {
        filmApiInterface.updateGenre(genre)
        //genreDao.updateGenre(genre)
    }

    suspend fun updateCountry(country: Country) {
        //countryDao.updateCountry(country)
        filmApiInterface.updateCountry(country)
    }

    suspend fun delFilm(film: Film) {
        //filmDao.deleteFilm(film)
        filmApiInterface.deleteFilm(film.id)
    }

    suspend fun updateFilm(film: Film) {
        filmApiInterface.updateFilm(film)
    }
}