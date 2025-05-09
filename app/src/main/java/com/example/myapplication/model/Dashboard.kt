package com.example.myapplication.model
import java.util.UUID

class Dashboard {
    data class UserProfile(
        val name: String,
        val profession: String,
        val rating: Double,
        val profileImageUrl: String? = null // Optional: URL for the profile picture
    )

    data class BookingSummary(
        val totalBookings: Int,
        val completedBookings: Int,
        val totalEarnings: Double
    )

    data class Booked(
        val id: String = UUID.randomUUID().toString(),
        val serviceTitle: String,
        val customerName: String,
        val customerProfileImageUrl: String? = null, // Optional
        val dateTime: String,
        val location: String? = null, // Optional
        val customerEmail: String? = null, // Optional
        val status: BookingStatus,
        val price: String? = null, // Optional
        val isDetailsShown: Boolean = false
    )

    enum class BookingStatus {
        ACTIVE,
        PENDING,
        COMPLETED
    }
}