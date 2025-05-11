package com.example.bookingapi.repositories

import com.example.bookingapi.models.Booking
import com.example.bookingapi.models.Bookings
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class BookingRepository {
    fun getAllBookings(): List<Booking> = transaction {
        Bookings.selectAll().map { rowToBooking(it) }
    }
    
    fun getBookingsByStatus(status: String): List<Booking> = transaction {
        Bookings.select { Bookings.status eq status }.map { rowToBooking(it) }
    }
    
    fun getBookingById(id: Int): Booking? = transaction {
        Bookings.select { Bookings.id eq id }.singleOrNull()?.let { rowToBooking(it) }
    }
    
    fun createBooking(booking: Booking): Int = transaction {
        Bookings.insert {
            it[serviceName] = booking.serviceName
            it[providerName] = booking.providerName
            it[dateTime] = booking.dateTime
            it[customerName] = booking.customerName
            it[price] = booking.price
            it[location] = booking.location
            it[status] = booking.status
        }[Bookings.id]
    }
    
    fun updateBookingStatus(id: Int, status: String) = transaction {
        Bookings.update({ Bookings.id eq id }) {
            it[Bookings.status] = status
        }
    }
    
    private fun rowToBooking(row: ResultRow): Booking = Booking(
        id = row[Bookings.id],
        serviceName = row[Bookings.serviceName],
        providerName = row[Bookings.providerName],
        dateTime = row[Bookings.dateTime],
        customerName = row[Bookings.customerName],
        price = row[Bookings.price],
        location = row[Bookings.location],
        status = row[Bookings.status]
    )
}
