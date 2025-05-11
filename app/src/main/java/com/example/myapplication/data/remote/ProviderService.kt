package com.example.myapplication.data.remote

import com.example.myapplication.data.model.Provider
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProviderService {

    @GET("providers/{id}")
    fun getProviderById(@Path("id") providerId: Int): Call<Provider>

    @GET("providers")
    fun getAllProviders(): Call<List<Provider>>

    @POST("providers")
    fun createProvider(@Body provider: Provider): Call<Provider>
}
