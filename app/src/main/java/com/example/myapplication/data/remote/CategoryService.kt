package com.example.myapplication.data.remote

import com.example.myapplication.data.model.Category
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoryService {

    @GET("categories")
    suspend fun getCategories(): List<Category>

    @GET("categories/{id}")
    suspend fun getCategoryById(@Path("id") categoryId: Int): Category

    companion object
}
