package ru.greatdevelopers.android_application.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.greatdevelopers.android_application.data.dao.*
import ru.greatdevelopers.android_application.data.model.*

@Database(
    entities = [Cinema::class, Favourite::class, Film::class, FilmCinema::class, User::class, Genre::class, Country::class],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cinemaDao(): CinemaDao
    abstract fun favouriteDao(): FavouriteDao
    abstract fun filmDao(): FilmDao
    abstract fun filmCinemaDao(): FilmCinemaDao
    abstract fun userDao(): UserDao
    abstract fun genreDao(): GenreDao
    abstract fun countryDao(): CountryDao

    /*companion object {
        @Volatile
        var INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context, scope: CoroutineScope): AppDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "catalogDB"
                ).addCallback(DatabaseCallback(scope)).build()

                INSTANCE = instance

                instance
            }
        }

        fun destroyDataBase() {
            INSTANCE = null
        }
    }

    private class DatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        /**
         * Override the onCreate method to populate the database.
         */
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            // If you want to keep the data through app restarts,
            // comment out the following line.
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.userDao(), database.filmDao())
                }
            }
        }

        suspend fun populateDatabase(userDao: UserDao, filmDao: FilmDao) {

            var admin =
                User(name = "admin", login = "admin", password = "password", userType = "admin")
            userDao.insertUser(admin)

        }
    }*/
}