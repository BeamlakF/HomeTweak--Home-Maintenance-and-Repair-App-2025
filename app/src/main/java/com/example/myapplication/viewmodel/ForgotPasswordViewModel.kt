package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.remote.RetrofitInstance
import com.example.myapplication.data.remote.model.ForgotPasswordRequest
import com.example.myapplication.data.remote.model.ForgotPasswordResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ForgotPasswordViewModel : ViewModel() {

    suspend fun sendResetCode(request: ForgotPasswordRequest): Result<ForgotPasswordResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.authApi.forgotPassword(request)
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
