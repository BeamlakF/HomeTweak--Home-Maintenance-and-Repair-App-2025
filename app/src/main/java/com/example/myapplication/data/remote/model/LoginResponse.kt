package com.example.myapplication.data.remote.model

data class LoginResponse(
    val token: String,
    val userId: String,
    val role: String
)
