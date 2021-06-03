package ru.greatdevelopers.android_application.data.respmodel

data class ResponseCinema(
    var url: String,

    var name: String,

    var email: String
    ) {
    override fun toString(): String {
        return name
    }
}