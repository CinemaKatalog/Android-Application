package ru.greatdevelopers.android_application.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.greatdevelopers.android_application.FilmRepository
import ru.greatdevelopers.android_application.data.model.Film
import ru.greatdevelopers.android_application.ui.mainscreen.adapters.FilmListItem

class FavouriteViewModel (private val repository: FilmRepository, private val userId: Int): ViewModel() {

    fun initialRequest(){
        viewModelScope.launch {
            loadFavourites.postValue(repository.getFavouriteFilmsWithExtra(userId))
        }
    }

    private val loadFavourites = MutableLiveData<List<FilmListItem>>(emptyList())
    val favoriteFilms: LiveData<List<FilmListItem>>
        get() = loadFavourites

}