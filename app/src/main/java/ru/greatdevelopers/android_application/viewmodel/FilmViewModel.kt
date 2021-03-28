package ru.greatdevelopers.android_application.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.greatdevelopers.android_application.data.model.*
import ru.greatdevelopers.android_application.data.repo.CinemaRepository
import ru.greatdevelopers.android_application.data.repo.FilmRepository
import ru.greatdevelopers.android_application.data.repo.ProfileRepository
import ru.greatdevelopers.android_application.ui.filmscreen.CinemaListItem

class FilmViewModel(
    private val filmRepository: FilmRepository,
    private val cinemaRepository: CinemaRepository,
    private val profileRepository: ProfileRepository,
    private val filmId: Int
) : ViewModel() {
    private val loadUser = MutableLiveData<User>()
    val user: LiveData<User>
        get() = loadUser

    private val loadFilmInfo = MutableLiveData<Film>()
    val film: LiveData<Film>
        get() = loadFilmInfo


    private val loadGenreInfo = MutableLiveData<Genre>()
    val genre: LiveData<Genre>
        get() = loadGenreInfo

    private val loadCountryInfo = MutableLiveData<Country>()
    val country: LiveData<Country>
        get() = loadCountryInfo

    private val loadCinemaInfo = MutableLiveData<List<CinemaListItem>>()
    val cinema: LiveData<List<CinemaListItem>>
        get() = loadCinemaInfo



    fun initialRequest(user_id: Int, onFoundUser: (user: User?)-> Unit) {
        viewModelScope.launch {
            val tmpUser = profileRepository.getUserById(user_id)
            loadUser.postValue(tmpUser)
            onFoundUser(tmpUser)
            val tmpFilm = filmId.let { filmRepository.getFilmById(it) }
            loadFilmInfo.postValue(tmpFilm)
            //loadFavourite.postValue(filmRepository.getFavouriteById(filmId, user_id))
            loadCinemaInfo.postValue(cinemaRepository.getFilmCinemaWithName(filmId))
            loadGenreInfo.postValue(tmpFilm?.let { filmRepository.getGenreById(it.genre) })
            loadCountryInfo.postValue(tmpFilm?.let { filmRepository.getCountryById(it.country) })
            //onFoundUser
        }
    }

    fun favouriteRequest(user_id: Int){
        viewModelScope.launch {
            loadFavourite.postValue(filmRepository.getFavouriteById(filmId, user_id))
        }
    }

    fun deleteFilm(film: Film){
        viewModelScope.launch {
            filmRepository.delFilm(film)
        }
    }

    fun deleteFavourite(favourite: Favourite){
        viewModelScope.launch {
            filmRepository.delFavourite(favourite)
        }
    }

    private val loadFavourite = MutableLiveData<Favourite>()
    val favourite: LiveData<Favourite>
        get() = loadFavourite

    fun insertFavourite(favourite: Favourite, onInsert: () -> Unit){
        viewModelScope.launch {
            filmRepository.insertFavourite(favourite)
            onInsert()
        }
    }
}