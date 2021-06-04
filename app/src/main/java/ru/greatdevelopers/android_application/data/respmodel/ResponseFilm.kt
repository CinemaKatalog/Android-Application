package ru.greatdevelopers.android_application.data.respmodel

import ru.greatdevelopers.android_application.data.model.Country
import ru.greatdevelopers.android_application.data.model.Genre

data class ResponseFilm(
    val id: Long = 0,
    var name: String,
    var genre: Genre,
    var country: Country,
    var producer: String,
    var description: String,
    var poster: String,
    var year: Int,
    var rating: Float
    )
