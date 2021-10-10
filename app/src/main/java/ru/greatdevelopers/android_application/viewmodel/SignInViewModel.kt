package ru.greatdevelopers.android_application.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.greatdevelopers.android_application.data.model.User
import ru.greatdevelopers.android_application.data.repo.ProfileRepository
import ru.greatdevelopers.android_application.data.repo.UserRepository
import ru.greatdevelopers.android_application.data.reqmodel.LoginUser

class SignInViewModel(
    private val profileRepo: ProfileRepository,
    private val userRepo: UserRepository
) : ViewModel() {

    private val loadUser = MutableLiveData<User>()
    val user: LiveData<User>
        get() = loadUser

    fun loginRequest(loginUser: LoginUser, onResult: (user: User?) -> Unit) {
        viewModelScope.launch {
            //val tmpUser = userRepo.getUserByLogin(login)
            val tmpUser = userRepo.loginUser(loginUser)
            if (tmpUser != null) {
                loadUser.postValue(tmpUser)
            }
            onResult(tmpUser)
        }
    }

    fun saveUser(user: User) {
        viewModelScope.launch {
            userRepo.writeCurrentUserIdToShPref(user.id)
            userRepo.writeUserToInternal(user)
            //println(userRepo.getCurrentUserIdFromShPref())
        }
    }
}