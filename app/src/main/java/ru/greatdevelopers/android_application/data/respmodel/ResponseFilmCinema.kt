package ru.greatdevelopers.android_application.data.respmodel

data class ResponseFilmCinema(
    var responseFilm: ResponseFilm,
    var responseCinema: ResponseCinema,
    val pageUrl: String,
    var price:Float,
    var rating:Float
    )


