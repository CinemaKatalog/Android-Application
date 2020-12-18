package ru.greatdevelopers.android_application.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [
    Index(value = ["genre_name"], unique = true)
])
data class Genre(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "genre_id") val id: Int = 0,
                 @ColumnInfo(name = "genre_name") var name: String){

    override fun toString(): String {
        return name
    }
}
