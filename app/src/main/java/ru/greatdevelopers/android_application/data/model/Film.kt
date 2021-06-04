package ru.greatdevelopers.android_application.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(
    entity = Genre::class,
    parentColumns = ["genre_id"], childColumns = ["genre"]
),
    ForeignKey(
        entity = Country::class,
        parentColumns = ["country_id"], childColumns = ["country"]
    )])
data class Film(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "film_id") val id: Long = 0,
    @ColumnInfo(name = "film_name") var name: String,
    var genre: Long,
    var country: Long,
    var producer: String,
    var description: String,
    var poster: String,
    var year: Int,
    var rating: Float){

}