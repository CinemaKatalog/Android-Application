package ru.greatdevelopers.android_application.network

import retrofit2.Call
import retrofit2.http.*
import ru.greatdevelopers.android_application.data.model.Cinema
import ru.greatdevelopers.android_application.data.model.FilmCinema
import ru.greatdevelopers.android_application.data.model.User
import ru.greatdevelopers.android_application.data.reqmodel.FilmCinemaRequest
import ru.greatdevelopers.android_application.data.reqmodel.LoginUser
import ru.greatdevelopers.android_application.data.respmodel.ResponseCinema
import ru.greatdevelopers.android_application.data.respmodel.ResponseFilmCinema
import ru.greatdevelopers.android_application.ui.filmscreen.CinemaListItem

interface CinemaApiInterface {
    @GET("film/loadFilmCinema")
    fun getFilmCinemaByIds(@Query("filmId") filmId: Long, @Query("siteUrl") siteUrl: String): Call<ResponseFilmCinema>

    @GET("edit/loadCinema")
    suspend fun getAllCinema(): List<Cinema>

    @PUT("film/loadFilmCinema/{id}")
    suspend fun getFilmCinemaWithName(@Path("id") id: Long): List<CinemaListItem>

    @POST("edit/filmCinema")
    suspend fun insertFilmCinema(@Body filmCinema: FilmCinema)

    @PUT("edit/filmCinema")
    suspend fun updateFilmCinema(@Body filmCinema: FilmCinema)

    @DELETE("edit/filmCinema")
    suspend fun deleteFilmCinema(@Query("filmId") filmId: Long, @Query("siteUrl") siteUrl: String)

    @POST("edit/cinema")
    suspend fun insertCinema(@Body cinema: Cinema)

    @PUT("edit/cinema")
    suspend fun updateCinema(@Body cinema: Cinema)

}