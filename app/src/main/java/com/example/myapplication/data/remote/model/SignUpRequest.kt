package com.example.myapplication.data.remote.model

data class SignUpRequest(
    val email: String,
    val password: String,
    val role: String
)
