package com.example.myapplication.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "https://yourapiurl.com/" // Change this to your actual base URL

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val bookingService: BookingService = retrofit.create(BookingService::class.java)
    val categoryService: CategoryService = retrofit.create(CategoryService::class.java)
    val providerService: ProviderService = retrofit.create(ProviderService::class.java)
    val userService: UserService = retrofit.create(UserService::class.java)
}
