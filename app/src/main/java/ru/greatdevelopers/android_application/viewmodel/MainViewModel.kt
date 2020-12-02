package ru.greatdevelopers.android_application.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.greatdevelopers.android_application.FilmRepository
import ru.greatdevelopers.android_application.ProfileRepository
import ru.greatdevelopers.android_application.data.model.Film
import ru.greatdevelopers.android_application.data.model.User

class MainViewModel(
    private val filmRepository: FilmRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val loadUser = MutableLiveData<User>()
    val user: LiveData<User>
        get() = loadUser

    private val loadFilms = MutableLiveData<List<Film>>()
    val films: LiveData<List<Film>>
        get() = loadFilms

    fun initialRequest(user_id: Int, onFoundUser: (user: User?)-> Unit) {
        viewModelScope.launch {
            val tmpUser = profileRepository.getUserById(user_id)
            loadUser.postValue(tmpUser)
            onFoundUser(tmpUser)
            loadFilms.postValue(filmRepository.getAllFilms())
        }
    }
}