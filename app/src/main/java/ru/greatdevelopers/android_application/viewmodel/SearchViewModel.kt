package ru.greatdevelopers.android_application.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.greatdevelopers.android_application.FilmRepository
import ru.greatdevelopers.android_application.data.model.Film

class SearchViewModel(private val repository: FilmRepository): ViewModel() {

    private val loadFilms = MutableLiveData<List<Film>>()
    val allFilms: LiveData<List<Film>>
        get() = loadFilms

    fun initialRequest(){
        viewModelScope.launch {
            loadFilms.postValue(repository.getAllFilms())
        }
    }

    fun insert(film: Film) = viewModelScope.launch() {
        repository.insertFilm(film)
    }
}