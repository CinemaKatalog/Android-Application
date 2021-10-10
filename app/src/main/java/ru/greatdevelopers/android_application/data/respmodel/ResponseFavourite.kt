package ru.greatdevelopers.android_application.data.respmodel

import ru.greatdevelopers.android_application.data.model.User


data class ResponseFavourite(

    val user: User,
    val film: ResponseFilm
)

