package ru.greatdevelopers.android_application.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "user_id") val id: Int? = null,
    @ColumnInfo(name = "user_name") var name: String,
    var login: String,
    var password: String,
    @ColumnInfo(name = "user_type") var userType: String) {


}