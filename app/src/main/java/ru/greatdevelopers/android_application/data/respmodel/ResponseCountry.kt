package ru.greatdevelopers.android_application.data.respmodel


data class ResponseCountry(

    val id: Long = 0,

    var name: String
    ){

    override fun toString(): String {
        return name
    }
}