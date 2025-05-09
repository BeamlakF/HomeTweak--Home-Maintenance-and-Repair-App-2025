package com.example.myapplication.model
import java.util.UUID

data class Booking(
    val id: String = UUID.randomUUID().toString(), // Auto-generated unique ID
    val customerId: String,
    val providerId: String,
    val serviceType: String,
    val serviceProvider: String,
    val price: String,
    val dateTime: String,
    val status: BookingStatus,
    val providerProfilePictureUrl: String? = null // Keep null; UI will handle the fallback
) {
    enum class BookingStatus {
        ACTIVE, PENDING, COMPLETED
    }
}
