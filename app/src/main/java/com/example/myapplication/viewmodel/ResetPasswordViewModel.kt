package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.remote.RetrofitInstance
import com.example.myapplication.data.remote.model.ResetPasswordRequest
import com.example.myapplication.data.remote.model.ResetPasswordResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ResetPasswordViewModel : ViewModel() {

    suspend fun resetPassword(request: ResetPasswordRequest): Result<ResetPasswordResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.authApi.resetPassword(request)
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
