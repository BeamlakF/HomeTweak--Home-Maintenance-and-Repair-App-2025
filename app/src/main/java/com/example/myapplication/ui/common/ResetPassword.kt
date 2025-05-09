package com.example.myapplication.ui.common

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.data.remote.model.ResetPasswordRequest
import com.example.myapplication.viewmodel.ResetPasswordViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@Composable
fun ResetPasswordScreen(
    onPasswordResetSuccess: () -> Unit,
    onBackToLogin: () -> Unit,
    viewModel: ResetPasswordViewModel = viewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var resetCode by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var message by remember { mutableStateOf<String?>(null) }
    var error by remember { mutableStateOf<String?>(null) }
    var email by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFFF9F5)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Image(
                painter = painterResource(id = R.drawable.hometweak_logo),
                contentDescription = "Logo",
                modifier = Modifier.height(150.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Reset Password",
                fontSize = 32.sp,
                color = Color(0xFF04285E),
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Enter your email", color = Color(0xFF4F5255)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(4.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    unfocusedContainerColor = Color(0xFFFFF1E8)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = resetCode,
                onValueChange = { resetCode = it },
                placeholder = { Text("Enter reset code", color = Color(0xFF4F5255)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(4.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    unfocusedContainerColor = Color(0xFFFFF1E8)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                placeholder = { Text("New password", color = Color(0xFF4F5255)) },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Color(0xFF4F5255)) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(4.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    unfocusedContainerColor = Color(0xFFFFF1E8)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = { Text("Confirm password", color = Color(0xFF4F5255)) },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Color(0xFF4F5255)) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(4.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    unfocusedContainerColor = Color(0xFFFFF1E8)
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (newPassword == confirmPassword) {
                        coroutineScope.launch {
                            val result = viewModel.resetPassword(
                                ResetPasswordRequest(email, resetCode, newPassword)
                            )
                            result.onSuccess {
                                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                                onPasswordResetSuccess()
                            }.onFailure {
                                error = it.localizedMessage ?: "Reset failed"
                            }
                        }
                    } else {
                        error = "Passwords do not match"
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6B00)),
                shape = RoundedCornerShape(40),
                modifier = Modifier
                    .fillMaxWidth(0.65f)
                    .height(60.dp)
            ) {
                Text("Reset Password", fontSize = 20.sp, color = Color.White)
            }

            error?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Text(it, color = Color.Red)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Back to Login",
                color = Color(0xFFFF6B00),
                fontSize = 16.sp,
                modifier = Modifier.clickable { onBackToLogin() }
            )
        }
    }
}
