package ru.greatdevelopers.android_application.network

import retrofit2.Response
import retrofit2.http.*
import ru.greatdevelopers.android_application.data.model.User
import ru.greatdevelopers.android_application.data.reqmodel.LoginUser

interface UserApiInterface {
    @POST("login/signin")
    suspend fun signIn(@Body loginUser: LoginUser): Response<User>

    @POST("login/signup")
    suspend fun signUp(@Body user: User): Response<User>

    @PUT("profile/")
    suspend fun updateUser(@Body user: User): Response<User>

    @GET("profile/{id}")
    suspend fun getUserById(@Path("id") id: Long): Response<User?>
}