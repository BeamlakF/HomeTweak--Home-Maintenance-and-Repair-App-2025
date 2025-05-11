package com.example.myapplication.data.repository

import com.example.myapplication.R
import com.example.myapplication.data.model.Booking
import com.example.myapplication.data.remote.RetrofitClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookingRepository {

    private val bookingService = RetrofitClient.bookingService

    // Fake a static profile picture (use a resource ID from drawable)
    fun getProfilePicture(): Flow<Int> = flow {
        emit(R.drawable.profile) // Replace with actual logic if available
    }

    // Retrofit callback wrapped in Flow for getAllBookings
    fun getAllBookings(): Flow<List<Booking>> = flow {
        val result = MutableStateFlow<List<Booking>>(emptyList())
        bookingService.getAllBookings().enqueue(object : Callback<List<Booking>> {
            override fun onResponse(call: Call<List<Booking>>, response: Response<List<Booking>>) {
                response.body()?.let { result.value = it }
            }

            override fun onFailure(call: Call<List<Booking>>, t: Throwable) {
                result.value = emptyList()
            }
        })
        emitAll(result)
    }

    suspend fun cancelBooking(bookingId: Int): Boolean {
        // Implement this using a suspend function call with Retrofit if you have it
        // For now, simulate success
        return true
    }

    fun createBooking(booking: Booking, callback: (Booking?) -> Unit) {
        bookingService.createBooking(booking).enqueue(object : Callback<Booking> {
            override fun onResponse(call: Call<Booking>, response: Response<Booking>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<Booking>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun getBookingById(bookingId: Int, callback: (Booking?) -> Unit) {
        bookingService.getBookingById(bookingId).enqueue(object : Callback<Booking> {
            override fun onResponse(call: Call<Booking>, response: Response<Booking>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<Booking>, t: Throwable) {
                callback(null)
            }
        })
    }
}
