package ru.greatdevelopers.android_application.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [
    Index(value = ["country_name"], unique = true)
])
data class Country(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "country_id") val id: Int = 0,
                   @ColumnInfo(name = "country_name") var name: String){

    override fun toString(): String {
        return name
    }
}
