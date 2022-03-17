package ru.greatdevelopers.android_application.data.repo

import ru.greatdevelopers.android_application.data.dao.CinemaDao
import ru.greatdevelopers.android_application.data.dao.FilmCinemaDao
import ru.greatdevelopers.android_application.data.model.Cinema
import ru.greatdevelopers.android_application.data.model.FilmCinema
import ru.greatdevelopers.android_application.network.CinemaApiInterface
import ru.greatdevelopers.android_application.ui.filmscreen.CinemaListItem

class CinemaRepository(
    private val filmCinemaDao: FilmCinemaDao,
    private val cinemaDao: CinemaDao,
    private val cinemaApiInterface: CinemaApiInterface
) {
    suspend fun getFilmCinema(filmId: Int): List<FilmCinema> {
        return filmCinemaDao.getFilmCinemaById(filmId)
    }

    suspend fun getFilmCinemaByIds(filmId: Long, siteUrl: String): FilmCinema? {
        /*val data = MutableLiveData<ResponseFilmCinema>()

        cinemaApiInterface.getFilmCinemaByIds(filmId, siteUrl)
            .enqueue(object : Callback<ResponseFilmCinema> {
                override fun onFailure(call: Call<ResponseFilmCinema>, t: Throwable) {
                    data.value = null
                }

                override fun onResponse(
                    call: Call<ResponseFilmCinema>,
                    response: Response<ResponseFilmCinema>
                ) {
                    val res = response.body()
                    if (response.code() == 200 && res != null) {
                        data.value = res
                    } else {
                        data.value = null
                    }
                }
            })*/

        var data = cinemaApiInterface.getFilmCinemaByIds(filmId, siteUrl)

        return FilmCinema(
            pageUrl = data.pageUrl,
            filmId = data.film.id,
            siteUrl = data.cinema.url,
            price = data.price,
            rating = data.rating
        )
        //return filmCinemaDao.getFilmCinemaByIds(filmId, siteUrl)
    }

    suspend fun getCinemaById(cinemaUrl: String): Cinema {
        return cinemaDao.getCinemaByUrl(cinemaUrl)
    }

    suspend fun getCinemaByName(name: String): Cinema? {
        return cinemaDao.getCinemaByName(name)
    }

    suspend fun getAllCinema(): List<Cinema> {
        /*var data = List<Cinema>()

        cinemaApiInterface.getAllCinema()
            .enqueue(object : Callback<List<Cinema>> {
                override fun onFailure(call: Call<List<Cinema>>, t: Throwable) {
                    //data = null
                }

                override fun onResponse(
                    call: Call<List<Cinema>>,
                    response: Response<List<Cinema>>
                ) {
                    val res = response.body()
                    if (response.code() == 200 && res != null) {
                        data = res
                    } else {
                        //data.value = null
                    }
                }
            })


        return data*/
        //return cinemaDao.getAllCinema()
        return cinemaApiInterface.getAllCinema()
    }

    suspend fun getFilmCinemaByFilm(filmId: Long): List<CinemaListItem> {
        //return filmCinemaDao.getFilmCinemaWithName(filmId)
        return cinemaApiInterface.getFilmCinemaWithName(filmId)
    }

    suspend fun insertFilmCinema(filmCinema: FilmCinema) {
        //filmCinemaDao.insertFilmCinema(filmCinema)
        cinemaApiInterface.insertFilmCinema(filmCinema)
    }

    suspend fun updateFilmCinema(filmCinema: FilmCinema) {
        cinemaApiInterface.updateFilmCinema(filmCinema)
    }

    suspend fun insertCinema(cinema: Cinema) {
        //cinemaDao.insertCinema(cinema)
        cinemaApiInterface.insertCinema(cinema)
    }

    suspend fun updateCinema(cinema: Cinema) {
        //cinemaDao.updateCinema(cinema)
        cinemaApiInterface.updateCinema(cinema)
    }

    suspend fun deleteFilmCinema(filmCinema: FilmCinema) {
        //filmCinemaDao.deleteFilmCinema(filmCinema)
        cinemaApiInterface.deleteFilmCinema(filmCinema.filmId, filmCinema.siteUrl)
    }
}