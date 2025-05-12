package com.example.myapplication.data

import com.example.myapplication.model.Dashboard
import kotlinx.coroutines.delay
import java.util.UUID

class FakeDashboardRepo : DashboardRepo {
    private val providerProfile = Dashboard.UserProfile(
        name = "Yohannes Afework",
        profession = "Master Electrician",
        rating = 4.9,
        profileImageUrl = "url_to_john_electric_profile" // Or null for default
    )

    private val bookingSummary = Dashboard.BookingSummary(
        totalBookings = 75,
        completedBookings = 68,
        totalEarnings = 3450.75
    )

    private val pendingBookings = mutableListOf(
        Dashboard.Booked(
            id = UUID.randomUUID().toString(),
            serviceTitle = "New Outlet Installation",
            customerName = "Eve Adams",
            dateTime = "May 10, 2025. 11:00 AM",
            status = Dashboard.BookingStatus.PENDING,
            location = "123 Elm Street",
            customerEmail = "eve.a@email.com"
        ),
        Dashboard.Booked(
            id = UUID.randomUUID().toString(),
            serviceTitle = "Troubleshoot Power Outage",
            customerName = "Frank Miller",
            dateTime = "May 10, 2025. 02:30 PM",
            status = Dashboard.BookingStatus.PENDING
        ),
        Dashboard.Booked(
            id = UUID.randomUUID().toString(),
            serviceTitle = "Wiring Check",
            customerName = "Grace Taylor",
            dateTime = "May 11, 2025. 09:30 AM",
            status = Dashboard.BookingStatus.PENDING,
            location = "789 Pine Lane"
        )
    )

    private val activeBookings = mutableListOf(
        Dashboard.Booked(
            id = UUID.randomUUID().toString(),
            serviceTitle = "Ceiling Fan Repair",
            customerName = "Grace White",
            dateTime = "May 9, 2025. 09:00 AM",
            status = Dashboard.BookingStatus.ACTIVE,
            location = "456 Oak Avenue"
        ),
        Dashboard.Booked(
            id = UUID.randomUUID().toString(),
            serviceTitle = "Smart Home Setup",
            customerName = "Kevin Hill",
            dateTime = "May 9, 2025. 01:00 PM",
            status = Dashboard.BookingStatus.ACTIVE
        )
    )

    private val completedBookings = mutableListOf(
        Dashboard.Booked(
            id = UUID.randomUUID().toString(),
            serviceTitle = "Kitchen Wiring",
            customerName = "Henry Brown",
            dateTime = "May 8, 2025. 01:00 PM",
            status = Dashboard.BookingStatus.COMPLETED,
            price = "120.00"
        ),
        Dashboard.Booked(
            id = UUID.randomUUID().toString(),
            serviceTitle = "Switch Replacement",
            customerName = "Ivy Green",
            dateTime = "May 7, 2025. 04:00 PM",
            status = Dashboard.BookingStatus.COMPLETED,
            price = "45.50"
        ),
        Dashboard.Booked(
            id = UUID.randomUUID().toString(),
            serviceTitle = "Outdoor Lighting Install",
            customerName = "Jack Stone",
            dateTime = "May 6, 2025. 10:30 AM",
            status = Dashboard.BookingStatus.COMPLETED,
            price = "80.00"
        )
    )

    override suspend fun getServiceProviderProfile(): Dashboard.UserProfile {
        delay(100) // Simulate network delay
        return providerProfile
    }

    override suspend fun getBookingSummary(): Dashboard.BookingSummary {
        delay(150)
        return bookingSummary
    }

    override suspend fun getPendingBookings(): List<Dashboard.Booked> {
        delay(200)
        return pendingBookings.toList()
    }

    override suspend fun getActiveBookings(): List<Dashboard.Booked> {
        delay(180)
        return activeBookings.toList()
    }

    override suspend fun getCompletedBookings(): List<Dashboard.Booked> {
        delay(220)
        return completedBookings.toList()
    }

    override suspend fun confirmBooking(bookingId: String): Boolean {
        val bookingIndex = pendingBookings.indexOfFirst { it.id == bookingId }
        if (bookingIndex != -1) {
            val updatedBooking = pendingBookings[bookingIndex].copy(status = Dashboard.BookingStatus.ACTIVE)
            pendingBookings.removeAt(bookingIndex)
            activeBookings.add(updatedBooking)
            return true
        }
        return false
    }

    override suspend fun declineBooking(bookingId: String): Boolean {
        return pendingBookings.removeIf { it.id == bookingId }
    }
}