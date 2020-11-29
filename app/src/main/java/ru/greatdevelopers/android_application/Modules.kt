package ru.greatdevelopers.android_application

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.greatdevelopers.android_application.data.dao.FavouriteDao
import ru.greatdevelopers.android_application.data.dao.FilmDao
import ru.greatdevelopers.android_application.data.dao.UserDao
import ru.greatdevelopers.android_application.data.db.AppDatabase
import ru.greatdevelopers.android_application.viewmodel.FavouriteViewModel
import ru.greatdevelopers.android_application.viewmodel.SearchViewModel
import ru.greatdevelopers.android_application.viewmodel.SignInViewModel
import ru.greatdevelopers.android_application.viewmodel.SignUpViewModel


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

    single { filmRepository(get(), get()) }

    single { profileRepository(get()) }
}

