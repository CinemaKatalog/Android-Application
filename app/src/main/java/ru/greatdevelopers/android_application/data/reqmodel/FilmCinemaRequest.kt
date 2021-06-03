package ru.greatdevelopers.android_application.data.reqmodel

class FilmCinemaRequest(
    val filmId: Long,
    val cinemaUrl: String,
    val pageUrl: String,
    var price: Float,
    var rating: Float
) {
}