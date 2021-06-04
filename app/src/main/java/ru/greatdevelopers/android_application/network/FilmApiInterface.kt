package ru.greatdevelopers.android_application.network

import retrofit2.Call
import retrofit2.http.*
import ru.greatdevelopers.android_application.data.model.*
import ru.greatdevelopers.android_application.data.reqmodel.FavouriteRequest
import ru.greatdevelopers.android_application.data.reqmodel.SearchParams
import ru.greatdevelopers.android_application.data.respmodel.ResponseFavourite
import ru.greatdevelopers.android_application.data.respmodel.ResponseFilm
import ru.greatdevelopers.android_application.data.respmodel.ResponseFilmCinema
import ru.greatdevelopers.android_application.ui.filmscreen.CinemaListItem
import ru.greatdevelopers.android_application.ui.mainscreen.adapters.FilmListItem

interface FilmApiInterface {
    @GET("edit/loadGenre")
    suspend fun getAllGenre(): List<Genre>

    @GET("edit/loadCountry")
    suspend fun getAllCountry(): List<Country>

    @GET("film/loadGenre/{id}")
    suspend fun getGenreById(@Path("id") id: Long): Genre?

    @GET("film/loadCountry/{id}")
    suspend fun getCountryById(@Path("id") id: Long): Country?

    @GET("edit/loadFilmInfo/{id}")
    suspend fun getFilmById(@Path("id") id: Long): ResponseFilm?

    @GET("search/query")
    suspend fun getFilmByNameWithExtra(@Query("query") query: String): List<FilmListItem>

    @POST("search/params")
    suspend fun getFilmByParameters(@Body searchParams: SearchParams): List<FilmListItem>

    @GET("favourite/{id}")
    suspend fun getFavourFilmWithExtra(@Path("id") id: Long): List<FilmListItem>

    @GET("search/")
    suspend fun getFilmWithExtra(): List<FilmListItem>

    @POST("favourite/is_favourite")
    suspend fun getFavouriteById(@Body favouriteRequest: FavouriteRequest): ResponseFavourite?

    @DELETE("favourite/delete")
    suspend fun deleteFavourite(@Body favourite: Favourite)

    @DELETE("edit/film")
    suspend fun deleteFilm(@Query("id") id: Long)

    @POST("favourite/post")
    suspend fun insertFavourite(@Body favourite: Favourite)

    @POST("edit/genre")
    suspend fun insertGenre(@Body genre: Genre)

    @POST("edit/country")
    suspend fun insertCountry(@Body country: Country)

    @POST("edit/film")
    suspend fun insertFilm(@Body film: Film)

    @PUT("edit/genre")
    suspend fun updateGenre(@Body genre: Genre)

    @PUT("edit/country")
    suspend fun updateCountry(@Body country: Country)

    @PUT("edit/film")
    suspend fun updateFilm(@Body film: Film)
}