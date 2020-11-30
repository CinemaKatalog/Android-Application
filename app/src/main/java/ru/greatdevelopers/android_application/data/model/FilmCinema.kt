package ru.greatdevelopers.android_application.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(
    entity = Cinema::class,
    parentColumns = ["site_url"], childColumns = ["site_url"]
),
    ForeignKey(
        entity = Film::class,
        parentColumns = ["film_id"], childColumns = ["film_id"]
    )])
data class FilmCinema(@PrimaryKey @ColumnInfo(name = "page_url") val pageUrl: String,
                 @ColumnInfo(name = "film_id") var filmId: Int,
                 @ColumnInfo(name = "site_url") var siteUrl: String,
                 var price:Float,
                 var rating:Float) {

}