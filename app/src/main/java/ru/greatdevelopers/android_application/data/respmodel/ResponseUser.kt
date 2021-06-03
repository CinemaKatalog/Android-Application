package ru.greatdevelopers.android_application.data.respmodel


data class ResponseUser(
    val id: Long = 0,
    var name: String,
    var login: String,
    var password: String,
    var userType: String
)
