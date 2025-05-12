package com.example.myapplication.data.remote

import com.example.myapplication.data.model.Booking
import com.example.myapplication.data.model.Booking.BookingStatus
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.PUT

interface BookingService {

    @POST("bookings")
    fun createBooking(@Body booking: Booking): Call<Booking>

    @GET("bookings/user/{userId}")
    fun getBookingsByUserId(@Path("userId") userId: Int): Call<List<Booking>>

    @GET("bookings/provider/{providerId}")
    fun getBookingsByProviderId(@Path("providerId") providerId: Int): Call<List<Booking>>

    @PUT("bookings/{id}")
    fun updateBookingStatus(
        @Path("id") bookingId: Int,
        @Body status: BookingStatus
    ): Call<Booking>

    @GET("bookings/{id}")
    fun getBookingById(@Path("id") bookingId: Int): Call<Booking>
}