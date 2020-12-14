package ru.greatdevelopers.android_application.data.dao

import androidx.room.*
import ru.greatdevelopers.android_application.data.model.Country
import ru.greatdevelopers.android_application.data.model.Genre

@Dao
interface CountryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)

    suspend fun insertCountry(country: Country)

    @Update
    suspend fun updateCountry(country: Country)

    @Delete
    suspend fun deleteCountry(country: Country)

    @Query("SELECT * FROM Country")
    suspend fun getAllCountry(): List<Country>

    @Query("SELECT * FROM Country WHERE country_id == :id")
    suspend fun getCountryById(id: Int): Country?
}