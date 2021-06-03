package ru.greatdevelopers.android_application.data.reqmodel

class FilmRequest(
    val id : Long,
    val name: String,
    val genre: Long,
    val country: Long,
    val producer: String,
    val description: String,
    val poster: String,
    val year: Int,
    val rating: Float) {
}