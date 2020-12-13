package ru.greatdevelopers.android_application.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.greatdevelopers.android_application.CinemaRepository
import ru.greatdevelopers.android_application.FilmRepository
import ru.greatdevelopers.android_application.data.model.Cinema
import ru.greatdevelopers.android_application.data.model.Film
import ru.greatdevelopers.android_application.data.model.FilmCinema
import ru.greatdevelopers.android_application.ui.filmscreen.CinemaListItem

class EditViewModel(
    private val filmRepository: FilmRepository,
    private val cinemaRepository: CinemaRepository,
    private val filmId: Int? = null
) : ViewModel() {

    fun initialRequest() {
        viewModelScope.launch {
            loadFilmInfo.postValue(filmId?.let { filmRepository.getFilmById(it) })
            loadCinemaListInfo.postValue(filmId?.let { cinemaRepository.getFilmCinemaWithName(it) })
        }
    }

    private val loadFilmInfo = MutableLiveData<Film>()
    val film: LiveData<Film>
        get() = loadFilmInfo

    fun updateFilm(film: Film, onUpdate: () -> Unit) {
        viewModelScope.launch {
            filmRepository.updateFilm(film)
            initialRequest()
            onUpdate()
        }
    }

    fun insertFilm(film: Film, onInsert: () -> Unit) {
        viewModelScope.launch {
            filmRepository.insertFilm(film)
            //initialRequest()
            onInsert()
        }
    }

    fun insertFilmCinema(filmCinema: FilmCinema) {
        viewModelScope.launch {
            cinemaRepository.insertFilmCinema(filmCinema)
        }
    }

    fun updateFilmCinema(filmCinema: FilmCinema) {
        viewModelScope.launch {
            cinemaRepository.updateFilmCinema(filmCinema)
        }
    }

    fun insertCinema(cinema: Cinema, onInsert: () -> Unit) {
        viewModelScope.launch {
            cinemaRepository.insertCinema(cinema)
            onInsert()
        }
    }

    fun updateCinema(cinema: Cinema, onUpdate: () -> Unit) {
        viewModelScope.launch {
            cinemaRepository.updateCinema(cinema)
            onUpdate()
        }
    }

    private val loadCinemaListInfo = MutableLiveData<List<CinemaListItem>>()
    val cinemaList: LiveData<List<CinemaListItem>>
        get() = loadCinemaListInfo

    private val loadCinemaInfo = MutableLiveData<List<Cinema>>()
    val cinema: LiveData<List<Cinema>>
        get() = loadCinemaInfo

    private val loadFilmCinemaInfo = MutableLiveData<FilmCinema>()
    val filmCinema: LiveData<FilmCinema>
        get() = loadFilmCinemaInfo

    fun initialRequestCinemas(siteUrl: String? = null) {
        viewModelScope.launch {
            loadCinemaInfo.postValue(cinemaRepository.getAllCinema())
            loadFilmCinemaInfo.postValue(siteUrl?.let {
                cinemaRepository.getFilmCinemaByIds(
                    filmId!!,
                    it
                )
            })
        }
    }

    fun deleteFilmCinema(filmId: Int, siteUrl: String, onDelete: () -> Unit) {
        viewModelScope.launch {
            var filmCinema = cinemaRepository.getFilmCinemaByIds(filmId, siteUrl)
            filmCinema?.let { cinemaRepository.deleteFilmCinema(it) }
            onDelete()
        }
    }
    /*fun getCinemaByName(name: String): Cinema?{
        viewModelScope.launch {
            cinemaRepository.getCinemaByName(name)
        }
    }*/
}