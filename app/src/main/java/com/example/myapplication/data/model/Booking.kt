package com.example.myapplication.data.model

data class Booking(
    val id: Int,
    val userId: Int,
    val providerId: Int,
    val serviceType: String,
    val serviceDate: String,
    val bookingDate: String,
    val status: BookingStatus
) {
    enum class BookingStatus {
        ACTIVE,
        PENDING,
        COMPLETED,
        DECLINED
    }
}
