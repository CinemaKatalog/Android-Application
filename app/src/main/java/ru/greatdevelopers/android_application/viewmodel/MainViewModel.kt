package ru.greatdevelopers.android_application.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.greatdevelopers.android_application.FilmRepository
import ru.greatdevelopers.android_application.ProfileRepository
import ru.greatdevelopers.android_application.data.model.Film
import ru.greatdevelopers.android_application.data.model.User
import ru.greatdevelopers.android_application.ui.mainscreen.adapters.FilmListItem

class MainViewModel(
    private val filmRepository: FilmRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    /*private val loadUser = MutableLiveData<User>()
    val user: LiveData<User>
        get() = loadUser*/

    private val loadFilms = MutableLiveData<List<FilmListItem>>()
    val films: LiveData<List<FilmListItem>>
        get() = loadFilms

    private val loadFilmsRating = MutableLiveData<List<FilmListItem>>()
    val filmsRating: LiveData<List<FilmListItem>>
        get() = loadFilmsRating

    private val loadFilmsYear = MutableLiveData<List<FilmListItem>>()
    val filmsYear: LiveData<List<FilmListItem>>
        get() = loadFilmsYear

    private val loadFilmsGenre = MutableLiveData<List<FilmListItem>>()
    val filmsGenre: LiveData<List<FilmListItem>>
        get() = loadFilmsGenre

    fun initialRequest(/*user_id: Int,*/ onFoundUser: () -> Unit) {
        viewModelScope.launch {
            /*val tmpUser = profileRepository.getUserById(user_id)
            loadUser.postValue(tmpUser)*/
            onFoundUser()
            loadFilms.postValue(filmRepository.getFilmsWithExtra())
        }
    }

    fun initGroupsRequest(onInit: () -> Unit) {
        viewModelScope.launch {
            loadFilmsGenre.postValue(
                filmRepository.getFilmByParameters(
                    2,
                    null,
                    minRating = 0f,
                    maxRating = 5f,
                    minYear = 2020,
                    maxYear = 0
                )
            )
            loadFilmsYear.postValue(
                filmRepository.getFilmByParameters(
                    null,
                    null,
                    minRating = 0f,
                    maxRating = 5f,
                    minYear = 2020,
                    maxYear = 2020
                )
            )
            loadFilmsRating.postValue(
                filmRepository.getFilmByParameters(
                    null,
                    null,
                    minRating = 4f,
                    maxRating = 5f,
                    minYear = 2020,
                    maxYear = 0
                )
            )
            onInit()
        }
    }
}