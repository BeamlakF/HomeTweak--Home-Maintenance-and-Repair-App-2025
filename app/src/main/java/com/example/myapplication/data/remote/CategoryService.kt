package com.example.myapplication.data.remote

import com.example.myapplication.data.model.Category
import retrofit2.Call
import retrofit2.http.GET

interface CategoryService {

    @GET("categories")
    fun getAllCategories(): Call<List<Category>>
}
