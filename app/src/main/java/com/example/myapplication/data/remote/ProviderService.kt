package com.example.myapplication.data.remote

import com.example.myapplication.data.model.Provider
import retrofit2.Call
import retrofit2.http.*

interface ProviderService {

    @GET("providers/{id}")
    fun getProviderById(@Path("id") id: Int): Call<Provider>

    @POST("providers")
    fun createProvider(@Body provider: Provider): Call<Provider>

    @GET("providers")
    fun getAllProviders(): Call<List<Provider>>

    @GET("providers/category/{categoryId}")
    fun getProvidersByCategory(@Path("categoryId") categoryId: Int): Call<List<Provider>>

    @GET("providers/search")
    fun searchProviders(@Query("name") name: String): Call<List<Provider>>

    @PUT("providers/{id}")
    fun updateProvider(@Path("id") id: Int, @Body provider: Provider): Call<Provider>

    @DELETE("providers/{id}")
    fun deleteProvider(@Path("id") id: Int): Call<Void>
}
