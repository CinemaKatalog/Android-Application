package ru.greatdevelopers.android_application.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.greatdevelopers.android_application.ProfileRepository
import ru.greatdevelopers.android_application.data.model.User

class ProfileViewModel(private val repository: ProfileRepository) : ViewModel() {
    private val loadUser = MutableLiveData<User>()
    val user: LiveData<User>
        get() = loadUser

    fun initialRequest(user_id: Int) {
        viewModelScope.launch {
            val tmpUser = repository.getUserById(user_id)
            loadUser.postValue(tmpUser)
        }
    }

    fun updateUserInfo(user: User) {
        viewModelScope.launch {
            repository.updateUser(user)
            initialRequest(user.id!!)
        }
    }
}