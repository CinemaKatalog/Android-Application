package ru.greatdevelopers.android_application.network

import retrofit2.Call
import retrofit2.http.*
import ru.greatdevelopers.android_application.data.model.User
import ru.greatdevelopers.android_application.data.reqmodel.LoginUser

interface UserApiInterface {
    @POST("login/signin")
    suspend fun signIn(@Body loginUser: LoginUser): User
    //fun signIn(@Body loginUser: LoginUser): Call<User>

    @POST("login/signup")
    suspend fun signUp(@Body user: User): User

    @PUT("profile/")
    suspend fun updateUser(@Body user: User): User

    @GET("profile/{id}")
    suspend fun getUserById(@Path("id") id: Long): User
}