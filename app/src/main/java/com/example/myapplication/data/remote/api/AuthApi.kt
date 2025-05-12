package com.example.myapplication.data.remote.api

import com.example.myapplication.data.remote.model.ForgotPasswordRequest
import com.example.myapplication.data.remote.model.ForgotPasswordResponse
import com.example.myapplication.data.remote.model.LoginRequest
import com.example.myapplication.data.remote.model.LoginResponse
import com.example.myapplication.data.remote.model.ResetPasswordRequest
import com.example.myapplication.data.remote.model.ResetPasswordResponse
import com.example.myapplication.data.remote.model.SignUpRequest
import com.example.myapplication.data.remote.model.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("register")
    suspend fun signUp(@Body request: SignUpRequest): SignUpResponse

    @POST("login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("reset-password")
    suspend fun resetPassword(@Body request: ResetPasswordRequest): ResetPasswordResponse

    @POST("forgot-password")
    suspend fun forgotPassword(@Body request: ForgotPasswordRequest): ForgotPasswordResponse


}
