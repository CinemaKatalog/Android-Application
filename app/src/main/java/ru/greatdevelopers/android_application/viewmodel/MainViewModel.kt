package ru.greatdevelopers.android_application.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.greatdevelopers.android_application.data.repo.FilmRepository
import ru.greatdevelopers.android_application.data.repo.ProfileRepository
import ru.greatdevelopers.android_application.data.repo.UserRepository
import ru.greatdevelopers.android_application.ui.mainscreen.adapters.FilmListItem

class MainViewModel(
    private val filmRepository: FilmRepository,
    private val profileRepository: ProfileRepository,
    private val userRepo: UserRepository
) : ViewModel() {

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

    private val loadIsAdmin = MutableLiveData<Boolean>()
    val isAdmin: LiveData<Boolean>
        get() = loadIsAdmin

    fun initialRequest(/*userId: Int, */onFoundUser: () -> Unit) {
        viewModelScope.launch {
            //loadIsAdmin.postValue(userRepo.getUserById(userId)?.userType == "admin")
            val userId = userRepo.getCurrentUserIdFromShPref()
            loadIsAdmin.postValue(userRepo.getUserById(userId)?.userType == "admin")
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
                    minYear = 0,
                    maxYear = 2020
                )
            )
            loadFilmsYear.postValue(
                filmRepository.getFilmByParameters(
                    null,
                    null,
                    minRating = 0f,
                    maxRating = 5f,
                    minYear = 2021,
                    maxYear = 2021
                )
            )
            loadFilmsRating.postValue(
                filmRepository.getFilmByParameters(
                    null,
                    null,
                    minRating = 4f,
                    maxRating = 5f,
                    minYear = 0,
                    maxYear = 2020
                )
            )
            onInit()
        }
    }
}