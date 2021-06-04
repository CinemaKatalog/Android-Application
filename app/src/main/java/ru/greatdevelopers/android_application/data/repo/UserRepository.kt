package ru.greatdevelopers.android_application.data.repo

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.greatdevelopers.android_application.data.model.User
import ru.greatdevelopers.android_application.data.reqmodel.LoginUser
import ru.greatdevelopers.android_application.network.UserApiInterface
import java.io.File

class UserRepository(val context: Context, private val userApiInterface: UserApiInterface) {

    companion object {
        const val FILE_CURRENT_USER = "current_user"
    }

    // имитация данных из сервера
    private val networkUsers = mutableListOf<User>(
        User(1, "Nikola", "nik@mail.ru", "12345678", "admin"),
        User(2, "Farkhad", "far@mail.ru", "12345678", "user")
    )

    /**
     * SharedPreferences хранят только айди авторизованного юзера, либо -1 если юзер не авторизован
     */
    suspend fun writeCurrentUserIdToShPref(id: Int) {
        context.getSharedPreferences(User.SP_ID_KEY, Context.MODE_PRIVATE)
            .edit()
            .putInt(User.SP_ID_KEY, id)
            .apply()
    }

    suspend fun getCurrentUserIdFromShPref(): Int {
        return context.getSharedPreferences(User.SP_ID_KEY, Context.MODE_PRIVATE)
            .getInt(User.SP_ID_KEY, -1)
    }

    /**
     * Internal Storage хранит юзера по пути
     * /data/data/ru.greatdevelopers.android_application/files/current_user
     * Там лежит сериализованный Юзер, либо null если юзер не авторизован в приложении
     */
    suspend fun getUserById(id: Int): User? {
        return getUserFromInternalOrNull()
            ?: // имитация получения данных из сервера
            networkUsers.find { it.id == id }
    }

    suspend fun getUserByLogin(login: String): User? {
        return getUserFromInternalOrNull()
            ?: // имитация получения данных из сервера
            networkUsers.find { it.login == login }
    }

    suspend fun loginUser(loginUser: LoginUser): User? {
        val data = MutableLiveData<User>()

        userApiInterface.signIn(loginUser).enqueue(object : Callback<User> {
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
        return data.value
    }

    suspend fun registerUser(user: User): User? {
        val data = MutableLiveData<User>()

        userApiInterface.signUp(user).enqueue(object : Callback<User> {
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
        return data.value
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

}