package ru.greatdevelopers.android_application.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.greatdevelopers.android_application.ProfileRepository
import ru.greatdevelopers.android_application.data.model.Film
import ru.greatdevelopers.android_application.data.model.User

class SignInViewModel(private val repository: ProfileRepository):ViewModel() {

    private val loadUser = MutableLiveData<User>()
    val user: LiveData<User>
        get() = loadUser

    fun loginRequest(login: String, onFoundUser: (user: User)-> Unit){
        viewModelScope.launch {
            val tmpUser = repository.getUserByLogin(login)
            if (tmpUser != null){
                loadUser.postValue(tmpUser)

                onFoundUser(tmpUser)
            }
        }
    }
}