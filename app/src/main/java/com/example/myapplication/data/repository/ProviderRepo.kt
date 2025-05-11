package com.example.myapplication.data.repository

import android.companion.CompanionDeviceManager.Callback
import android.telecom.Call
import com.example.myapplication.data.model.Provider
import com.example.myapplication.data.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.Provider

class ProviderRepository {

    private val providerService = RetrofitClient.providerService

    fun getProviderById(providerId: Int, callback: (Provider?) -> Unit) {
        providerService.getProviderById(providerId).enqueue(object : Callback<Provider> {
            override fun onResponse(call: Call<Provider>, response: Response<Provider>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<Provider>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun getAllProviders(callback: (List<Provider>?) -> Unit) {
        providerService.getAllProviders().enqueue(object : Callback<List<Provider>> {
            override fun onResponse(call: Call<List<Provider>>, response: Response<List<Provider>>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<List<Provider>>, t: Throwable) {
                callback(null)
            }
        })
    }
}
