package ru.greatdevelopers.android_application.data.respmodel

data class ResponseFilm(
    val id: Long = 0,
    var name: String,
    var responseGenre: ResponseGenre,
    var responseCountry: ResponseCountry,
    var producer: String,
    var description: String,
    var poster: String,
    var year: Int,
    var rating: Float
    )
