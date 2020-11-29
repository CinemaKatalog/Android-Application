package ru.greatdevelopers.android_application

import ru.greatdevelopers.android_application.data.dao.UserDao
import ru.greatdevelopers.android_application.data.model.User

class ProfileRepository(private val userDao: UserDao) {
    suspend fun getUserById(userId: Int): User?{
        return userDao.getUserById(userId)
    }

    suspend fun getUserByLogin(login: String): User?{
        return userDao.getUserByLogin(login)
    }

    suspend fun insertUser(user: User){
        userDao.insertUser(user)
    }

    suspend fun deleteUser(user: User){
        userDao.deleteUser(user)
    }

    suspend fun updateUser(user: User){
        userDao.updateUser(user)
    }
}