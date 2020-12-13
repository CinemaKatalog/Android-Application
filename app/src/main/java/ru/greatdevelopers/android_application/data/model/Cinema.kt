package ru.greatdevelopers.android_application.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Cinema(@PrimaryKey @ColumnInfo(name = "site_url") var url: String,
             @ColumnInfo(name = "cinema_name") var name: String,
             var email: String) {

    override fun toString(): String {
        return name
    }
}