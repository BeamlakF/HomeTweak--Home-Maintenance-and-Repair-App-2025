package com.example.myapplication.data.remote

import com.example.myapplication.data.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.PUT

interface UserService {

    @POST("users")
    fun createUser(@Body user: User): Call<User>

    @GET("users/{id}")
    fun getUserById(@Path("id") userId: Int): Call<User>

    @PUT("users/{id}")
    fun updateUser(@Path("id") userId: Int, @Body user: User): Call<User>
}
