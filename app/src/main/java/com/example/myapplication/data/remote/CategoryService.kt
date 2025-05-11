package com.example.myapplication.data.remote

import com.example.myapplication.data.model.Category
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoryService {

    @GET("categories")
    fun getCategories(): Call<List<Category>>

    @GET("categories/{id}")
    fun getCategoryById(@Path("id") categoryId: Int): Call<Category>
}
