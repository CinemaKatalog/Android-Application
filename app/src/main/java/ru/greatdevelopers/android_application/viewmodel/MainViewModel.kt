package ru.greatdevelopers.android_application.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.greatdevelopers.android_application.data.FilmGroup
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

    private val loadTopFilms = MutableLiveData<ArrayList<FilmGroup>>()
    val topFilms: LiveData<ArrayList<FilmGroup>>
        get() = loadTopFilms

    private val loadIsAdmin = MutableLiveData<Boolean>()
    val isAdmin: LiveData<Boolean>
        get() = loadIsAdmin

    fun initialRequest(onFoundUser: () -> Unit) {
        viewModelScope.launch {
            val userId = userRepo.checkUserById(userRepo.getCurrentUserIdFromShPref())
            if (userId != -1L) {
                loadIsAdmin.postValue(userRepo.getUserById(userId)?.userType == "admin")
            } else {
                loadIsAdmin.postValue(false)
            }
            onFoundUser()
            //loadFilms.postValue(filmRepository.getFilmsWithExtra())
        }
    }

    fun initGroupsRequest(onInit: () -> Unit) {
        // рекомендации
        // популярное
        // новинки
        // популярные комедии
        /*
        viewModelScope.launch {
            val genreList = mutableListOf<FilmListItem>()
            filmRepository.getRecommendedFilms(userRepo.getCurrentUserIdFromShPref())
                *//*filmRepository.getFilmByParameters(
                2,
                null,
                minRating = 0f,
                maxRating = 10f,
                minYear = 0,
                maxYear = 2022
            )*//*.forEach {
                filmRepository.getPoster(it.poster)?.let { posterURI ->
                    FilmListItem(
                        it.film_id,
                        it.film_name,
                        it.genre_name,
                        posterURI,
                        it.rating
                    )
                }?.let { it ->
                    genreList.add(
                        it
                    )
                }
            }
            loadFilmsGenre.postValue(genreList)

            val yearList = mutableListOf<FilmListItem>()
            //filmRepository.getFilmByParameters(
            filmRepository.getRecFilmByParams(
                userRepo.getCurrentUserIdFromShPref(),
                null,
                null,
                minRating = 0f,
                maxRating = 10f,
                minYear = 2008,
                maxYear = 2009
            ).forEach {
                filmRepository.getPoster(it.poster)?.let { posterURI ->
                    FilmListItem(
                        it.film_id,
                        it.film_name,
                        it.genre_name,
                        posterURI,
                        it.rating
                    )
                }?.let { it ->
                    yearList.add(
                        it
                    )
                }
            }
            loadFilmsYear.postValue(yearList)

            val ratingList = mutableListOf<FilmListItem>()
            filmRepository.getFilmByParameters(
                *//*null,
                null,
                minRating = 8f,
                maxRating = 10f,
                minYear = 0,
                maxYear = 2022*//*
                null,
                null,
                minRating = 0f,
                maxRating = 10f,
                minYear = 2008,
                maxYear = 2009
            ).forEach {
                filmRepository.getPoster(it.poster)?.let { posterURI ->
                    FilmListItem(
                        it.film_id,
                        it.film_name,
                        it.genre_name,
                        posterURI,
                        it.rating
                    )
                }?.let { it ->
                    ratingList.add(
                        it
                    )
                }
            }

            loadFilmsRating.postValue(
                ratingList
            )
            onInit()
        }
*/
        val groups: ArrayList<FilmGroup> = ArrayList()
        viewModelScope.launch {
            val userId = userRepo.getCurrentUserIdFromShPref()
            //val userId = 3477L
            if (userId != -1L){
                val recommendedList = mutableListOf<FilmListItem>()
                filmRepository.getRecommendedFilms(userId)
                    .forEach {
                        filmRepository.getPoster(it.poster)?.let { posterURI ->
                            FilmListItem(
                                it.film_id,
                                it.film_name,
                                it.genre_name,
                                posterURI,
                                it.rating
                            )
                        }?.let { it ->
                            recommendedList.add(
                                it
                            )
                        }
                    }
                groups.add(FilmGroup("Рекомендации", recommendedList))
            }



            val tops = filmRepository.getTopFilms()
            tops.forEach {top->
                val list = mutableListOf<FilmListItem>()

                top.films.forEach {
                    filmRepository.getPoster(it.poster)?.let { posterURI ->
                        FilmListItem(
                            it.film_id,
                            it.film_name,
                            it.genre_name,
                            posterURI,
                            it.rating
                        )
                    }?.let { it ->
                        list.add(
                            it
                        )
                    }
                }
                groups.add(FilmGroup(top.name, list))
            }
            loadTopFilms.postValue(groups)
            onInit()
        }
    }
}