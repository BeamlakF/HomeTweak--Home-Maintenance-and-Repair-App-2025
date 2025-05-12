package com.example.myapplication.data.repository

import com.example.myapplication.data.remote.RetrofitInstance
import com.example.myapplication.data.remote.model.*

class AuthRepository {

    private val authApi = RetrofitInstance.authApi

    suspend fun signUp(request: SignUpRequest): Result<SignUpResponse> {
        return try {
            val response = authApi.signUp(request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(request: LoginRequest): Result<LoginResponse> {
        return try {
            val response = authApi.login(request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun resetPassword(request: ResetPasswordRequest): Result<ResetPasswordResponse> {
        return try {
            val response = authApi.resetPassword(request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
