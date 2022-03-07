package ru.greatdevelopers.android_application.data.repo

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import ru.greatdevelopers.android_application.data.model.User
import ru.greatdevelopers.android_application.data.reqmodel.LoginUser
import ru.greatdevelopers.android_application.network.UserApiInterface
import ru.greatdevelopers.android_application.utils.Utils.safeApiCall
import java.io.File

class UserRepository(val context: Context, private val userApiInterface: UserApiInterface) {

    companion object {
        const val FILE_CURRENT_USER = "current_user"
    }

    /**
     * SharedPreferences хранят только айди авторизованного юзера, либо -1 если юзер не авторизован
     */
    suspend fun writeCurrentUserIdToShPref(id: Long) {
        context.getSharedPreferences(User.SP_ID_KEY, Context.MODE_PRIVATE)
            .edit()
            .putLong(User.SP_ID_KEY, id)
            .apply()
    }

    suspend fun getCurrentUserIdFromShPref(): Long {
        return context.getSharedPreferences(User.SP_ID_KEY, Context.MODE_PRIVATE)
            .getLong(User.SP_ID_KEY, -1L)
    }

    /**
     * Internal Storage хранит юзера по пути
     * /data/data/ru.greatdevelopers.android_application/files/current_user
     * Там лежит сериализованный Юзер, либо null если юзер не авторизован в приложении
     */
    suspend fun getUserById(id: Long): User? {
        /*val data = MutableLiveData<User>()

        userApiInterface.getUserById(id).enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                val res = response.body()
                if (response.code() == 200 && res!=null){
                    data.value = res
                }else{
                    data.value = null
                }
            }
        })
        return data.value*/

        //println("/n $id")
        //
        return getUserFromInternalOrNull() ?: getUserFromApiOrNull(id)
    }

    private suspend fun getUserFromApiOrNull(id: Long): User? {

        return if(id == -1L) {
            null
        }else {
            val user = safeApiCall(
                call = { userApiInterface.getUserById(id) },
                errorMessage = "Error Fetching User"
            )
            if (user != null) {
                user
            } else {
                writeCurrentUserIdToShPref(-1)
                writeUserToInternal(null)
                null
            }
        }
    }

    suspend fun checkUserById(id: Long): Long {
        return if(id == -1L) {
            -1L
        }
        else {
            if (safeApiCall(
                    call = { userApiInterface.getUserById(id) },
                    errorMessage = "Error Fetching User"
                ) != null
            ) {
                id
            } else {
                writeCurrentUserIdToShPref(-1)
                writeUserToInternal(null)
                //getCurrentUserIdFromShPref()
                -1L
            }
        }
    }


    suspend fun loginUser(loginUser: LoginUser): User? {

        return safeApiCall(
            call = { userApiInterface.signIn(loginUser) },
            errorMessage = "Error Login User"
        )
    }

    suspend fun registerUser(user: User): User? {


        return safeApiCall(
            call = { userApiInterface.signUp(user) },
            errorMessage = "Error Register User"
        )
    }

    suspend fun writeUserToInternal(user: User?) {
        return File(context.filesDir, FILE_CURRENT_USER)
            .outputStream().bufferedWriter().use {
                it.write(Gson().toJson(user))
            }
    }

    private suspend fun getUserFromInternalOrNull(): User? {
        return try {
            File(context.filesDir, FILE_CURRENT_USER)
                .inputStream().bufferedReader().use {
                    it.readText()
                }.let {
                    val user: User? = Gson().fromJson(it, User::class.java)
                    user
                }
        } catch (e: Exception) {
            Log.e("UserRepository", e.localizedMessage)
            return null
        }
    }


    suspend fun updateUser(user: User) {

        safeApiCall(
            call = { userApiInterface.updateUser(user) },
            errorMessage = "Error Fetching User"
        )
    }

}