package com.example.myapplication.data.remote

import com.example.myapplication.data.model.Booking
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BookingService {

    @POST("bookings")
    fun createBooking(@Body booking: Booking): Call<Booking>

    @GET("bookings/{id}")
    fun getBookingById(@Path("id") bookingId: Int): Call<Booking>

    @GET("bookings/user/{userId}")
    fun getBookingsByUserId(@Path("userId") userId: Int): Call<List<Booking>>
}
