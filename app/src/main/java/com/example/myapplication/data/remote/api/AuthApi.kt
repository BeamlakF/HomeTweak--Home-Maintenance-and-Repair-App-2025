package com.example.myapplication.data.remote.api

import com.example.myapplication.data.remote.model.LoginRequest
import com.example.myapplication.data.remote.model.LoginResponse
import com.example.myapplication.data.remote.model.SignUpRequest
import com.example.myapplication.data.remote.model.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/auth/signup")
    suspend fun signUp(@Body request: SignUpRequest): SignUpResponse

    @POST("/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}
