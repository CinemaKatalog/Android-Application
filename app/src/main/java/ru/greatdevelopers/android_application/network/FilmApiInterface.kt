package ru.greatdevelopers.android_application.network

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
import ru.greatdevelopers.android_application.data.model.Country
import ru.greatdevelopers.android_application.data.model.Favourite
import ru.greatdevelopers.android_application.data.model.Film
import ru.greatdevelopers.android_application.data.model.Genre
import ru.greatdevelopers.android_application.data.reqmodel.RatingRequest
import ru.greatdevelopers.android_application.data.reqmodel.UserFilmRequest
import ru.greatdevelopers.android_application.data.reqmodel.SearchParams
import ru.greatdevelopers.android_application.data.respmodel.ResponseFavourite
import ru.greatdevelopers.android_application.data.respmodel.ResponseFilm
import ru.greatdevelopers.android_application.data.respmodel.ResponsePoster
import ru.greatdevelopers.android_application.data.respmodel.ResponseTop
import ru.greatdevelopers.android_application.ui.mainscreen.adapters.FilmListItem

interface FilmApiInterface {
    @GET("edit/loadGenre")
    suspend fun getAllGenre(): List<Genre>

    @GET("edit/loadCountry")
    suspend fun getAllCountry(): List<Country>

    @Streaming
    @GET("film/loadPoster/{fileUUID}")
    suspend fun getPosterById(@Path("fileUUID") fileUUID: String): Response<ResponseBody>

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

    @POST("recommendations/{id}/params")
    suspend fun getRecFilmByParams(@Path("id") id: Long, @Body searchParams: SearchParams): List<FilmListItem>

    @GET("favourite/{id}")
    suspend fun getFavourFilmWithExtra(@Path("id") id: Long): List<FilmListItem>

    @GET("recommendations/{id}")
    suspend fun getRecommendedFilm(@Path("id") id: Long): List<FilmListItem>

    @GET("tops/")
    suspend fun getTopFilm(): List<ResponseTop>

    @GET("search/")
    suspend fun getFilmWithExtra(): List<FilmListItem>

    @POST("favourite/is_favourite")
    suspend fun getFavouriteById(@Body userFilmRequest: UserFilmRequest): ResponseFavourite?

    @POST("recommendations/rating")
    suspend fun getRatingById(@Body userFilmRequest: UserFilmRequest): Float?

    @DELETE("favourite/delete")
    suspend fun deleteFavourite(@Query("userId") userId: Long, @Query("filmId") filmId: Long)

    @DELETE("edit/film")
    suspend fun deleteFilm(@Query("id") id: Long)

    @POST("favourite/post")
    suspend fun insertFavourite(@Body favourite: Favourite)

    @POST("recommendations/set_rating")
    suspend fun insertRating(@Body ratingRequest: RatingRequest)

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

    @Multipart
    @POST("edit/poster")
    suspend fun insertPoster(@Part image: MultipartBody.Part): Response<ResponsePoster>
}