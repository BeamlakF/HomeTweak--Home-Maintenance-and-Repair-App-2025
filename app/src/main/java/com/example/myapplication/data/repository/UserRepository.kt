package com.example.myapplication.data.repository

import com.example.myapplication.data.model.User
import com.example.myapplication.data.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {

    private val userService = RetrofitClient.userService

    fun createUser(user: User, callback: (User?) -> Unit) {
        userService.createUser(user).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun getUserById(userId: Int, callback: (User?) -> Unit) {
        userService.getUserById(userId).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun updateUser(userId: Int, user: User, callback: (User?) -> Unit) {
        userService.updateUser(userId, user).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                callback(null)
            }
        })
    }
}
