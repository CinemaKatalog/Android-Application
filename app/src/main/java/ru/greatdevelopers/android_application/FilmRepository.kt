package ru.greatdevelopers.android_application

import kotlinx.coroutines.flow.Flow
import ru.greatdevelopers.android_application.data.dao.FavouriteDao
import ru.greatdevelopers.android_application.data.dao.FilmCinemaDao
import ru.greatdevelopers.android_application.data.dao.FilmDao
import ru.greatdevelopers.android_application.data.dao.UserDao
import ru.greatdevelopers.android_application.data.model.Favourite
import ru.greatdevelopers.android_application.data.model.Film
import ru.greatdevelopers.android_application.data.model.FilmCinema

class FilmRepository(private val filmDao: FilmDao, private val favouriteDao: FavouriteDao) {

    suspend fun getAllFilms(): List<Film>{
        return filmDao.getFilmAll()
    }

    suspend fun getFilmById(filmId: Int): Film?{
        return filmDao.getFilmById(filmId)
    }

    suspend fun getFavouriteFilms(userId: Int): List<Film> {
        return filmDao.getFavouriteFilms(userId)
    }

    suspend fun insertFavourite(favourite: Favourite){
        favouriteDao.insertFavourite(favourite)
    }

    suspend fun getFavouriteById(filmId: Int, userId: Int): Favourite?{
        return favouriteDao.getFavouriteById(filmId, userId)
    }
    suspend fun delFavourite(favourite: Favourite){
        favouriteDao.deleteFavourite(favourite)
    }

    suspend fun insertFilm(film: Film){
        filmDao.insertFilm(film)
    }

    suspend fun delFilm(film: Film){
        filmDao.deleteFilm(film)
    }

    suspend fun updateFilm(film: Film){
        filmDao.updateFilm(film)
    }
}