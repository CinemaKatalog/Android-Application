package ru.greatdevelopers.android_application.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.greatdevelopers.android_application.CinemaRepository
import ru.greatdevelopers.android_application.FilmRepository
import ru.greatdevelopers.android_application.ProfileRepository
import ru.greatdevelopers.android_application.data.model.Favourite
import ru.greatdevelopers.android_application.data.model.Film
import ru.greatdevelopers.android_application.data.model.User
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


    private val loadCinemaInfo = MutableLiveData<List<CinemaListItem>>()
    val cinema: LiveData<List<CinemaListItem>>
        get() = loadCinemaInfo



    fun initialRequest(user_id: Int, onFoundUser: (user: User?)-> Unit) {
        viewModelScope.launch {
            val tmpUser = profileRepository.getUserById(user_id)
            loadUser.postValue(tmpUser)
            onFoundUser(tmpUser)
            loadFilmInfo.postValue(filmId.let { filmRepository.getFilmById(it) })
            loadFavourite.postValue(filmRepository.getFavouriteById(filmId, user_id))
            loadCinemaInfo.postValue(cinemaRepository.getFilmCinemaWithName(filmId))
            onFoundUser
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

    fun insertFavourite(favourite: Favourite){
        viewModelScope.launch {
            filmRepository.insertFavourite(favourite)
        }
    }
}