package ru.greatdevelopers.android_application.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.greatdevelopers.android_application.data.model.User
import ru.greatdevelopers.android_application.data.repo.ProfileRepository
import ru.greatdevelopers.android_application.data.repo.UserRepository

class SignUpViewModel(
    private val repository: ProfileRepository,
    private val userRepo: UserRepository
) : ViewModel() {


    fun registerRequest(user: User, onResult: (user: User?) -> Unit) {
        viewModelScope.launch {
            val tmpUser = userRepo.registerUser(user)

            onResult(tmpUser)
        }
    }

}

