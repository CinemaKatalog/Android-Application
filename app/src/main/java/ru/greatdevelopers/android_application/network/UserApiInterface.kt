package ru.greatdevelopers.android_application.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.greatdevelopers.android_application.data.model.User
import ru.greatdevelopers.android_application.data.reqmodel.LoginUser

interface UserApiInterface {
    @POST("login/signin")
    fun signIn(@Body loginUser: LoginUser): Call<User>

    @POST("login/signup")
    fun signUp(@Body user: User): Call<User>
}