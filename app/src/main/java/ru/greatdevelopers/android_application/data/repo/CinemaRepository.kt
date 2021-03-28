package ru.greatdevelopers.android_application.data.repo

import ru.greatdevelopers.android_application.data.dao.CinemaDao
import ru.greatdevelopers.android_application.data.dao.FilmCinemaDao
import ru.greatdevelopers.android_application.data.model.Cinema
import ru.greatdevelopers.android_application.data.model.FilmCinema
import ru.greatdevelopers.android_application.ui.filmscreen.CinemaListItem

class CinemaRepository(private val filmCinemaDao: FilmCinemaDao, private val cinemaDao: CinemaDao) {
    suspend fun getFilmCinema(filmId: Int): List<FilmCinema> {
        return filmCinemaDao.getFilmCinemaById(filmId)
    }

    suspend fun getFilmCinemaByIds(filmId: Int, siteUrl: String): FilmCinema? {
        return filmCinemaDao.getFilmCinemaByIds(filmId, siteUrl)
    }

    suspend fun getCinemaById(cinemaUrl: String): Cinema {
        return cinemaDao.getCinemaByUrl(cinemaUrl)
    }

    suspend fun getCinemaByName(name: String): Cinema?{
        return cinemaDao.getCinemaByName(name)
    }

    suspend fun getAllCinema(): List<Cinema> {
        return cinemaDao.getAllCinema()
    }

    suspend fun getFilmCinemaWithName(filmId: Int): List<CinemaListItem>{
        return filmCinemaDao.getFilmCinemaWithName(filmId)
    }

    suspend fun insertFilmCinema(filmCinema: FilmCinema){
        filmCinemaDao.insertFilmCinema(filmCinema)
    }

    suspend fun updateFilmCinema(filmCinema: FilmCinema){
        filmCinemaDao.updateFilmCinema(filmCinema)
    }

    suspend fun insertCinema(cinema: Cinema){
        cinemaDao.insertCinema(cinema)
    }
    suspend fun updateCinema(cinema: Cinema){
        cinemaDao.updateCinema(cinema)
    }

    suspend fun deleteFilmCinema(filmCinema: FilmCinema){
        filmCinemaDao.deleteFilmCinema(filmCinema)
    }
}