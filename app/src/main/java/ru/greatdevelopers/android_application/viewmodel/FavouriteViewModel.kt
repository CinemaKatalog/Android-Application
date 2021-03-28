package ru.greatdevelopers.android_application.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.greatdevelopers.android_application.data.model.User
import ru.greatdevelopers.android_application.data.repo.FilmRepository
import ru.greatdevelopers.android_application.ui.mainscreen.adapters.FilmListItem

class FavouriteViewModel (private val repository: FilmRepository, private val user: User): ViewModel() {

    fun initialRequest(){
        viewModelScope.launch {
            loadFavourites.postValue(repository.getFavouriteFilmsWithExtra(user.id))
        }
    }

    private val loadFavourites = MutableLiveData<List<FilmListItem>>(emptyList())
    val favoriteFilms: LiveData<List<FilmListItem>>
        get() = loadFavourites

}