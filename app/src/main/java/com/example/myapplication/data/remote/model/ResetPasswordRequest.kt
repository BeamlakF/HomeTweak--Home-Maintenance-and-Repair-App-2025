package com.example.myapplication.data.remote.model

data class ResetPasswordRequest(
    val email: String,
    val resetCode: String,
    val newPassword: String
)
