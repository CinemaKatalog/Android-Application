package ru.greatdevelopers.android_application.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.greatdevelopers.android_application.ProfileRepository
import ru.greatdevelopers.android_application.data.model.User

class SignUpViewModel(private val repository: ProfileRepository):ViewModel() {

    fun insertUser(user: User) = viewModelScope.launch() {
        repository.insertUser(user)
    }

    private val loadUser = MutableLiveData<User>()
    val user: LiveData<User>
        get() = loadUser

    fun loginRequest(login: String){
        viewModelScope.launch {
            loadUser.postValue(repository.getUserByLogin(login))
        }
    }

}

