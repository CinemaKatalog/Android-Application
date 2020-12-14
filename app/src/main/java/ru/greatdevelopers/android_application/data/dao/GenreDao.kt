package ru.greatdevelopers.android_application.data.dao

import androidx.room.*
import ru.greatdevelopers.android_application.data.model.Cinema
import ru.greatdevelopers.android_application.data.model.Film
import ru.greatdevelopers.android_application.data.model.Genre

@Dao
interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGenre(genre: Genre)

    @Update
    suspend fun updateGenre(genre: Genre)

    @Delete
    suspend fun deleteGenre(genre: Genre)

    @Query("SELECT * FROM Genre")
    suspend fun getAllGenre(): List<Genre>

    @Query("SELECT * FROM Genre WHERE genre_id == :id")
    suspend fun getGenreById(id: Int): Genre?
}