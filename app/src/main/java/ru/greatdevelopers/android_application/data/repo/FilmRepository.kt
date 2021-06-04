package ru.greatdevelopers.android_application.data.repo

import ru.greatdevelopers.android_application.data.dao.CountryDao
import ru.greatdevelopers.android_application.data.dao.FavouriteDao
import ru.greatdevelopers.android_application.data.dao.FilmDao
import ru.greatdevelopers.android_application.data.dao.GenreDao
import ru.greatdevelopers.android_application.data.model.Country
import ru.greatdevelopers.android_application.data.model.Favourite
import ru.greatdevelopers.android_application.data.model.Film
import ru.greatdevelopers.android_application.data.model.Genre
import ru.greatdevelopers.android_application.ui.mainscreen.adapters.FilmListItem

class FilmRepository(private val filmDao: FilmDao, private val favouriteDao: FavouriteDao, private val genreDao: GenreDao, private val countryDao: CountryDao) {

    suspend fun getAllGenres(): List<Genre>{
        return genreDao.getAllGenre()
    }
    suspend fun getAllCountries(): List<Country>{
        return countryDao.getAllCountry()
    }

    suspend fun getGenreById(id: Long): Genre?{
        return genreDao.getGenreById(id)
    }
    suspend fun getCountryById(id: Long): Country?{
        return countryDao.getCountryById(id)
    }

    suspend fun getAllFilms(): List<Film>{
        return filmDao.getFilmAll()
    }

    suspend fun getFilmById(filmId: Long): Film?{
        return filmDao.getFilmById(filmId)
    }

    suspend fun getFilmByName(filmName: String): List<Film>{
        return filmDao.getFilmByName(filmName)
    }

    suspend fun getFilmByNameWithExtra(filmName: String): List<FilmListItem>{
        return filmDao.getFilmByNameWithExtra(filmName)
    }

    suspend fun getFilmByParameters(genre: Long?,
                                    country: Long?,
                                    minYear: Int,
                                    maxYear: Int,
                                    minRating: Float,
                                    maxRating: Float): List<FilmListItem>{
        return if(genre == null && country == null){
            filmDao.getFilmByParamsWGC(maxYear, minYear, minRating, maxRating)
        }else if(genre == null){
            filmDao.getFilmByParamsWG(country!!, maxYear, minYear, minRating, maxRating)
        }else if (country == null){
            filmDao.getFilmByParamsWC(genre, maxYear, minYear, minRating, maxRating)
        }else{
            filmDao.getFilmByAllParams(genre, country, maxYear, minYear, minRating, maxRating)
        }
    }

    suspend fun getFavouriteFilms(userId: Int): List<Film> {
        return filmDao.getFavouriteFilms(userId)
    }
    suspend fun getFavouriteFilmsWithExtra(userId: Int): List<FilmListItem> {
        return filmDao.getFavourFilmWithExtra(userId)
    }
    suspend fun getFilmsWithExtra(): List<FilmListItem> {
        return filmDao.getFilmWithExtra()
    }

    suspend fun insertFavourite(favourite: Favourite){
        favouriteDao.insertFavourite(favourite)
    }

    suspend fun getFavouriteById(filmId: Long, userId: Long): Favourite?{
        return favouriteDao.getFavouriteById(filmId, userId)
    }
    suspend fun delFavourite(favourite: Favourite){
        favouriteDao.deleteFavourite(favourite)
    }

    suspend fun insertFilm(film: Film){
        filmDao.insertFilm(film)
    }

    suspend fun insertGenre(genre: Genre){
        genreDao.insertGenre(genre)
    }

    suspend fun insertCountry(country: Country){
        countryDao.insertCountry(country)
    }

    suspend fun updateGenre(genre: Genre){
        genreDao.updateGenre(genre)
    }

    suspend fun updateCountry(country: Country){
        countryDao.updateCountry(country)
    }

    suspend fun delFilm(film: Film){
        filmDao.deleteFilm(film)
    }

    suspend fun updateFilm(film: Film){
        filmDao.updateFilm(film)
    }
}