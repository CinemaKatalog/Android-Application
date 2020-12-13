package ru.greatdevelopers.android_application.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.greatdevelopers.android_application.ProfileRepository
import ru.greatdevelopers.android_application.data.model.User

class ProfileViewModel(private val repository: ProfileRepository, private val userId: Int) : ViewModel() {
    private val loadUser = MutableLiveData<User>()
    val user: LiveData<User>
        get() = loadUser

    fun initialRequest() {
        viewModelScope.launch {
            val tmpUser = repository.getUserById(userId)
            loadUser.postValue(tmpUser)
        }
    }

    fun updateUserInfo(user: User, onInsert: () -> Unit) {
        viewModelScope.launch {
            repository.updateUser(user)
            initialRequest()
            onInsert()
        }
    }
}