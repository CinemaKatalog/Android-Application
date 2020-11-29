package ru.greatdevelopers.android_application.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Film(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "film_id") val id: Int? = null,
    @ColumnInfo(name = "film_name") var name: String,
    var genre: String,
    var country: String,
    var producer: String,
    var description: String,
    var poster: String,
    var year: Int,
    var rating: Float){

}