package ru.greatdevelopers.android_application.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.greatdevelopers.android_application.data.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM User WHERE login == :login")
    suspend fun getUserByLogin(login: String): User?

    @Query("SELECT * FROM User WHERE user_id == :id")
    suspend fun getUserById(id: Int): User?
}