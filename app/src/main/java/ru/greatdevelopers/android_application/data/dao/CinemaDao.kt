package ru.greatdevelopers.android_application.data.dao

import androidx.room.*
import ru.greatdevelopers.android_application.data.model.Cinema
import ru.greatdevelopers.android_application.data.model.Favourite

@Dao
interface CinemaDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(cinema: Cinema)

    @Update
    suspend fun updateUser(cinema: Cinema)

    @Delete
    suspend fun deleteUser(cinema: Cinema)

}