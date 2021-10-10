package ru.greatdevelopers.android_application.di

import androidx.room.Room
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.greatdevelopers.android_application.data.dao.*
import ru.greatdevelopers.android_application.data.db.AppDatabase
import ru.greatdevelopers.android_application.data.repo.CinemaRepository
import ru.greatdevelopers.android_application.data.repo.FilmRepository
import ru.greatdevelopers.android_application.data.repo.ProfileRepository
import ru.greatdevelopers.android_application.data.repo.UserRepository
import ru.greatdevelopers.android_application.network.CinemaApiInterface
import ru.greatdevelopers.android_application.network.FilmApiInterface
import ru.greatdevelopers.android_application.network.UserApiInterface
import ru.greatdevelopers.android_application.viewmodel.*

val appModule = module {
}

val viewModelModule = module {
    viewModel {
        SearchViewModel(get())
    }

    viewModel {
        FavouriteViewModel(get(), get())
    }

    viewModel {
        SignInViewModel(get(), get())
    }

    viewModel {
        SignUpViewModel(get(), get())
    }

    viewModel {
        MainViewModel(get(), get(), get())
    }

    viewModel {
        ProfileViewModel(get(), get())
    }

    viewModel { (filmId: Long?) ->
        EditViewModel(get(), get(), filmId)
    }

    viewModel { (filmId: Long) ->
        FilmViewModel(get(), get(), get(), get(), filmId)
    }
}

val apiModule = module {
    fun provideUserApi(retrofit: Retrofit): UserApiInterface {
        return retrofit.create(UserApiInterface::class.java)
    }

    fun provideFilmApi(retrofit: Retrofit): FilmApiInterface {
        return retrofit.create(FilmApiInterface::class.java)
    }

    fun provideCinemaApi(retrofit: Retrofit): CinemaApiInterface {
        return retrofit.create(CinemaApiInterface::class.java)
    }


    single { provideUserApi(get()) }
    single { provideFilmApi(get()) }
    single { provideCinemaApi(get()) }
}

//private const val BASEURL = "http://localhost:8080/mobileApi/"
private const val BASEURL = "http://10.0.2.2:8080/mobileApi/"

val netModule = module {
    /*single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor(androidContext()))
            .addInterceptor(HttpLoggingInterceptor(
                object : HttpLoggingInterceptor.Logger {
                    override fun log(message: String) {
                        Log.d("OkHttp", message)
                    }
                }
            ).apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            })
            .build()
    }*/



    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(100, java.util.concurrent.TimeUnit.SECONDS)
            .connectTimeout(100, java.util.concurrent.TimeUnit.SECONDS)
            .build()

    }

    fun provideGson(): Gson {
        return GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .create()
    }


    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()
    }

    single { provideHttpClient() }
    single { provideGson() }
    single { provideRetrofit(get(), get()) }

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
    single {
        get<AppDatabase>().genreDao()
    }

    single {
        get<AppDatabase>().countryDao()
    }
}

val repositoryModule = module {
    /*fun filmRepository(filmDao: FilmDao, favouriteDao: FavouriteDao, genreDao: GenreDao, countryDao: CountryDao, filmApiInterface: FilmApiInterface): FilmRepository {
        return FilmRepository(filmDao, favouriteDao, genreDao, countryDao, filmApiInterface)
    }*/

    fun profileRepository(userDao: UserDao): ProfileRepository {
        return ProfileRepository(userDao)
    }

    fun cinemaRepository(filmCinemaDao: FilmCinemaDao, cinemaDao: CinemaDao, cinemaApiInterface: CinemaApiInterface): CinemaRepository {
        return CinemaRepository(filmCinemaDao, cinemaDao, cinemaApiInterface)
    }

    single { FilmRepository(androidContext(), get(), get(), get(), get(), get()) }

    single { ProfileRepository(get()) }

    single { CinemaRepository(get(), get(), get()) }

    single { UserRepository(androidContext(), get()) }
}

