package ru.greatdevelopers.android_application.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE

@Entity(
    primaryKeys = ["user_id", "film_id"], foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["user_id"], childColumns = ["user_id"]
    ),
        ForeignKey(
            entity = Film::class,
            parentColumns = ["film_id"], childColumns = ["film_id"], onDelete = CASCADE
        )]
)
data class Favourite(
    @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "film_id") val filmId: Int
) {

}