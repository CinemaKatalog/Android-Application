package ru.greatdevelopers.android_application.data.respmodel

data class ResponseFilmCinema(
    var film: ResponseFilm,
    var cinema: ResponseCinema,
    val pageUrl: String,
    var price:Float,
    var rating:Float
    )


