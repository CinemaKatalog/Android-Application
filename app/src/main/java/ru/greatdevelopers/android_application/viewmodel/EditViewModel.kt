package ru.greatdevelopers.android_application.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.greatdevelopers.android_application.CinemaRepository
import ru.greatdevelopers.android_application.FilmRepository
import ru.greatdevelopers.android_application.data.model.*
import ru.greatdevelopers.android_application.ui.filmscreen.CinemaListItem

class EditViewModel(
    private val filmRepository: FilmRepository,
    private val cinemaRepository: CinemaRepository,
    private val filmId: Int? = null
) : ViewModel() {

    private val loadGenreListInfo = MutableLiveData<List<Genre>>()
    val genreList: LiveData<List<Genre>>
        get() = loadGenreListInfo
    private val loadCountryListInfo = MutableLiveData<List<Country>>()
    val countryList: LiveData<List<Country>>
        get() = loadCountryListInfo
    private val loadFilmInfo = MutableLiveData<Film>()
    val film: LiveData<Film>
        get() = loadFilmInfo


    fun initialRequest() {
        viewModelScope.launch {

            loadCinemaListInfo.postValue(filmId?.let { cinemaRepository.getFilmCinemaWithName(it) })
            loadFilmInfo.postValue(filmId?.let { filmRepository.getFilmById(it) })

        }
    }

    fun spinnerInitRequest(){
        viewModelScope.launch {
            loadGenreListInfo.postValue(filmRepository.getAllGenres())
            loadCountryListInfo.postValue(filmRepository.getAllCountries())
        }
    }

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
            onInsert()
        }
    }

    fun insertGenre(genre: Genre, onInsert: () -> Unit) {
        viewModelScope.launch {
            filmRepository.insertGenre(genre)
            onInsert()
        }
    }

    fun insertCountry(country: Country, onInsert: () -> Unit) {
        viewModelScope.launch {
            filmRepository.insertCountry(country)
            onInsert()
        }
    }

    fun updateGenre(genre: Genre, onUpdate: () -> Unit) {
        viewModelScope.launch {
            filmRepository.updateGenre(genre)
            onUpdate()
        }
    }

    fun updateCountry(country: Country, onUpdate: () -> Unit) {
        viewModelScope.launch {
            filmRepository.updateCountry(country)
            onUpdate()
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

    fun deleteFilmCinema(filmId: Int, siteUrl: String, onDelete: () -> Unit) {
        viewModelScope.launch {
            var filmCinema = cinemaRepository.getFilmCinemaByIds(filmId, siteUrl)
            filmCinema?.let { cinemaRepository.deleteFilmCinema(it) }
            onDelete()
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
            loadFilmCinemaInfo.postValue(siteUrl?.let {
                cinemaRepository.getFilmCinemaByIds(
                    filmId!!,
                    it
                )
            })
        }
    }

    fun cinemaInitRequest(){
        viewModelScope.launch {
            loadCinemaInfo.postValue(cinemaRepository.getAllCinema())
        }
    }
}