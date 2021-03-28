package ru.greatdevelopers.android_application.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.greatdevelopers.android_application.data.model.Country
import ru.greatdevelopers.android_application.data.model.Film
import ru.greatdevelopers.android_application.data.model.Genre
import ru.greatdevelopers.android_application.data.repo.FilmRepository
import ru.greatdevelopers.android_application.ui.mainscreen.adapters.FilmListItem

class SearchViewModel(private val repository: FilmRepository): ViewModel() {

    private val loadFilms = MutableLiveData<List<FilmListItem>>()
    val allFilms: LiveData<List<FilmListItem>>
        get() = loadFilms
    private val loadGenreInfo = MutableLiveData<List<Genre>>()
    val genre: LiveData<List<Genre>>
        get() = loadGenreInfo
    private val loadCountryInfo = MutableLiveData<List<Country>>()
    val country: LiveData<List<Country>>
        get() = loadCountryInfo

    fun initialRequest(){
        viewModelScope.launch {
            loadFilms.postValue(repository.getFilmsWithExtra())
        }
    }

    fun initialRequestOptions(){
        viewModelScope.launch {
            loadCountryInfo.postValue(repository.getAllCountries())
            loadGenreInfo.postValue(repository.getAllGenres())
        }
    }

    fun searchRequest(query: String?){
        viewModelScope.launch {
            loadFilms.postValue(query?.let { repository.getFilmByNameWithExtra(it) })
        }
    }

    fun searchByParamsRequest(genre: Int? = null,
                              country: Int? = null,
                              minYear: Int,
                              maxYear: Int,
                              minRating: Float,
                              maxRating: Float){
        viewModelScope.launch {
            loadFilms.postValue(repository.getFilmByParameters(genre, country, maxYear, minYear, minRating, maxRating))
        }
    }

    fun insert(film: Film) = viewModelScope.launch() {
        repository.insertFilm(film)
    }


}