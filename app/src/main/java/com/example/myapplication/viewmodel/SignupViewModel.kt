package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.remote.RetrofitInstance
import com.example.myapplication.data.remote.model.SignUpRequest
import com.example.myapplication.data.remote.model.SignUpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SignupViewModel : ViewModel() {

    suspend fun signUp(request: SignUpRequest): Result<SignUpResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.authApi.signUp(request)
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
