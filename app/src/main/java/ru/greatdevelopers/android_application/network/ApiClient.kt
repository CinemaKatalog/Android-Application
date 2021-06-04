package ru.greatdevelopers.android_application.network

import android.icu.util.TimeUnit
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//const val BASEURL = "http://localhost:8080/mobileApi/"
class ApiClient {
    /*companion object{
        private var retrofit: Retrofit?=null
        fun getApiClient(): Retrofit {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(100, java.util.concurrent.TimeUnit.SECONDS)
                .connectTimeout(100, java.util.concurrent.TimeUnit.SECONDS)
                .build()
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASEURL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
            return retrofit!!
        }
    }*/
}