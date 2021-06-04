package ru.greatdevelopers.android_application.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.greatdevelopers.android_application.data.model.User
import ru.greatdevelopers.android_application.data.repo.FilmRepository
import ru.greatdevelopers.android_application.data.repo.UserRepository
import ru.greatdevelopers.android_application.ui.mainscreen.adapters.FilmListItem

class FavouriteViewModel (private val repository: FilmRepository, private val userRepo: UserRepository): ViewModel() {

    private val userLiveData = MutableLiveData<User?>()
    val user: LiveData<User?>
        get() = userLiveData

    fun loadUser() {
        viewModelScope.launch {
            val id = userRepo.getCurrentUserIdFromShPref()
            println(id)
            if (id == -1L) {
                userLiveData.postValue(null)
            } else {
                val user = userRepo.getUserById(id)
                println(user)
                userLiveData.postValue(user)
            }
        }
    }

    private val loadFavourites = MutableLiveData<List<FilmListItem>>(emptyList())
    val favoriteFilms: LiveData<List<FilmListItem>>
        get() = loadFavourites

    fun initialRequest(){
        viewModelScope.launch {
            loadFavourites.postValue(repository.getFavouriteFilmsWithExtra(userRepo.getCurrentUserIdFromShPref()))
        }
    }
}