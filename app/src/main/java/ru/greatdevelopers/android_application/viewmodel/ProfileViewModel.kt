package ru.greatdevelopers.android_application.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.greatdevelopers.android_application.data.model.User
import ru.greatdevelopers.android_application.data.repo.ProfileRepository
import ru.greatdevelopers.android_application.data.repo.UserRepository

class ProfileViewModel(
    private val repository: ProfileRepository,
    private val userRepo: UserRepository
) : ViewModel() {

    private val userLiveData = MutableLiveData<User?>()
    val user: LiveData<User?>
        get() = userLiveData


    fun updateUserInfo(name: String, login: String, password: String, onUpdated: () -> Unit) {
        viewModelScope.launch {
            val userEdited = User(
                user.value!!.id,
                name, login, password,
                user.value!!.userType
            )
            repository.updateUser(userEdited)
            userRepo.writeUserToInternal(userEdited)
            userLiveData.postValue(userEdited)
            onUpdated()
        }
    }

    fun signOut() {
        viewModelScope.launch {
            userRepo.writeCurrentUserIdToShPref(-1)
            userRepo.writeUserToInternal(null)
        }
    }

    fun loadUser() {
        viewModelScope.launch {
            val id = userRepo.getCurrentUserIdFromShPref()
            if (id == -1) {
                userLiveData.postValue(null)
            } else {
                userLiveData.postValue(userRepo.getUserById(id))
            }
        }
    }
}