package com.example.myapplication.data.remote

import retrofit2.Call
import retrofit2.http.*

data class RegisterRequest(val username: String, val password: String, val role: String)
data class LoginRequest(val username: String, val password: String)
data class LoginResponse(val token: String)

interface UserService {

    @POST("users/register")
    fun register(@Body request: RegisterRequest): Call<Void>

    @POST("users/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("users/service-provider-only")
    fun accessAsProvider(@Header("Authorization") token: String): Call<Map<String, String>>

    @GET("users/customer-only")
    fun accessAsCustomer(@Header("Authorization") token: String): Call<Map<String, String>>

    @DELETE("users/{id}")
    fun deleteUser(@Path("id") userId: Int): Call<Void>
}
