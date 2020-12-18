package ru.greatdevelopers.android_application.data.model

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(primaryKeys = ["film_id", "site_url"], foreignKeys = [ForeignKey(
    entity = Cinema::class,
    parentColumns = ["site_url"], childColumns = ["site_url"]
),
    ForeignKey(
        entity = Film::class,
        parentColumns = ["film_id"], childColumns = ["film_id"], onDelete = CASCADE
    )],
    indices = [
    Index(value = ["page_url"], unique = true)
])
data class FilmCinema(@ColumnInfo(name = "page_url") val pageUrl: String,
                 @ColumnInfo(name = "film_id") var filmId: Int,
                 @ColumnInfo(name = "site_url") var siteUrl: String,
                 var price:Float,
                 var rating:Float) {

}