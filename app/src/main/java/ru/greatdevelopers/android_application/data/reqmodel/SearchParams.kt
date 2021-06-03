package ru.greatdevelopers.android_application.data.reqmodel

class SearchParams(var genre: Long? = null,
                   var country: Long? = null,
                   var minYear: Int,
                   var maxYear: Int,
                   var minRating: Float,
                   var maxRating: Float) {
}