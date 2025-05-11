package com.example.myapplication.data.model


data class Provider(
    val id: Int,
    val userId: Int,
    val categoryId: Int,
    val certificate: String?,
    val location: String?,
    val imageUrl: String?,
    val phoneNumber: String,
    val hourlyRate: String,
    val yearsOfExperience: Int,
    val rating: Float?,
    val serviceDescription: String?
)
