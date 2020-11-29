package ru.greatdevelopers.android_application.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.greatdevelopers.android_application.FilmRepository
import ru.greatdevelopers.android_application.data.model.Film

class FavouriteViewModel (private val repository: FilmRepository, private val userId: Int): ViewModel() {

    fun initialRequest(){
        viewModelScope.launch {
            loadFavourites.postValue(repository.getFavouriteFilms(userId))
        }
    }

    private val loadFavourites = MutableLiveData<List<Film>>(emptyList())
    val favoriteFilms: LiveData<List<Film>>
        get() = loadFavourites


    //val allFilms: LiveData<List<Film>> = repository.allFilms.asLiveData()
}