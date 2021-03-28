package ru.greatdevelopers.android_application.data.repo

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import ru.greatdevelopers.android_application.data.model.User
import java.io.File

class UserRepository(val context: Context) {

    companion object {
        const val FILE_CURRENT_USER = "current_user"
    }

    // имитация данных из сервера
    private val networkUsers = mutableListOf<User>(
        User(1, "Nikola", "nik@mail.ru", "12345678", "admin"),
        User(2, "Farkhad", "far@mail.ru", "12345678", "admin")
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

    suspend fun getCurrentUserIdfromShPref(): Int {
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