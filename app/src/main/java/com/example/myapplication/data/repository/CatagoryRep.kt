package com.example.myapplication.data.repository

import android.telecom.Call
import com.example.myapplication.data.model.Category
import com.example.myapplication.data.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale.Category
import javax.security.auth.callback.Callback

class CategoryRepository {

    private val categoryService = RetrofitClient.categoryService

    fun getCategories(callback: (List<Category>?) -> Unit) {
        categoryService.getCategories().enqueue(object : Callback<List<Category>> {
            override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                callback(null)
            }
        })
    }
}
