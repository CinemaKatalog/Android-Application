package ru.greatdevelopers.android_application

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.greatdevelopers.android_application.data.dao.*
import ru.greatdevelopers.android_application.data.db.AppDatabase
import ru.greatdevelopers.android_application.viewmodel.*


val viewModelModule = module {
    viewModel {
        SearchViewModel(get())
    }

    viewModel { (userId: Int) ->
        FavouriteViewModel(get(), userId)
    }

    viewModel {
        SignInViewModel(get())
    }

    viewModel {
        SignUpViewModel(get())
    }

    viewModel {
        MainViewModel(get(), get())
    }

    viewModel { (userId: Int) ->
        ProfileViewModel(get(), userId)
    }

    viewModel {(filmId: Int?) ->
        EditViewModel(get(), get(), filmId)
    }

    viewModel {(filmId: Int) ->
        FilmViewModel(get(), get(), get(), filmId)
    }
}

val databaseModule = module {

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "catalogDB")
            .build()
    }
    single {
        get<AppDatabase>().filmDao()
    }

    single {
        get<AppDatabase>().favouriteDao()
    }

    single {
        get<AppDatabase>().userDao()
    }

    single {
        get<AppDatabase>().filmCinemaDao()
    }

    single {
        get<AppDatabase>().cinemaDao()
    }
}

val repositoryModule = module {
    fun filmRepository(filmDao: FilmDao, favouriteDao: FavouriteDao): FilmRepository {
        return FilmRepository(filmDao, favouriteDao)
    }

    fun profileRepository(userDao: UserDao): ProfileRepository {
        return ProfileRepository(userDao)
    }

    fun cinemaRepository(filmCinemaDao: FilmCinemaDao, cinemaDao: CinemaDao): CinemaRepository {
        return CinemaRepository(filmCinemaDao, cinemaDao)
    }

    single { filmRepository(get(), get()) }

    single { profileRepository(get()) }

    single { cinemaRepository(get(), get()) }
}

