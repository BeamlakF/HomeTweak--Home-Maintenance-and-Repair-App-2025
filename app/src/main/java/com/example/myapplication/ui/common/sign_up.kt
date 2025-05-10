package com.example.myapplication.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.data.remote.model.SignUpRequest
import com.example.myapplication.utils.AuthManager
import com.example.myapplication.viewmodel.SignupViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    onSignInClick: () -> Unit,
    viewModel: SignupViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val roleOptions = listOf("customer", "serviceProvider")
    var selectedRole by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }

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

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                "Sign Up",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color(0xFF04285E),
                    fontSize = 36.sp
                ),
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))
            Text("Create new account", style = MaterialTheme.typography.headlineSmall.copy(
                color = Color(0xFF4F5255)
            ))

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Enter your email", style = MaterialTheme.typography.headlineSmall.copy(color = Color(0xFF4F5255))) },
                leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "Email Icon", tint = Color(0xFF4F5255)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(4.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    unfocusedContainerColor = Color(0xFFFFF1E8)
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Enter your password", style = MaterialTheme.typography.headlineSmall.copy(color = Color(0xFF4F5255))) },
                leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "Lock Icon", tint = Color(0xFF4F5255)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(4.dp),
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    unfocusedContainerColor = Color(0xFFFFF1E8)
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedRole,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    placeholder = { Text("Select your role", style = MaterialTheme.typography.headlineSmall.copy(color = Color(0xFF4F5255))) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    shape = RoundedCornerShape(4.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        unfocusedContainerColor = Color(0xFFFFF1E8)
                    )
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(Color(0xFFFFF1E8))
                ) {
                    roleOptions.forEach { role ->
                        DropdownMenuItem(
                            text = { Text(role, style = MaterialTheme.typography.headlineSmall.copy(color = Color(0xFF4F5255))) },
                            onClick = {
                                selectedRole = role
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        val result = viewModel.signUp(SignUpRequest(email, password, selectedRole))
                        result.onSuccess {
                            successMessage = it.message
                            errorMessage = null
                        }.onFailure {
                            errorMessage = it.localizedMessage ?: "Sign up failed"
                            successMessage = null
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6B00)),
                shape = RoundedCornerShape(40),
                modifier = Modifier
                    .fillMaxWidth(0.65f)
                    .height(65.dp)
            ) {
                Text("Sign Up", fontSize = 28.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))
            errorMessage?.let { Text(it, color = Color.Red) }
            successMessage?.let { Text(it, color = Color.Green) }

            Spacer(modifier = Modifier.height(24.dp))

            Row {
                Text("Already have an account?", style = MaterialTheme.typography.headlineSmall.copy(color = Color(0xFF4F5255), fontSize = 16.sp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    "Sign in",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFFFF6B00),
                    fontSize = 16.sp,
                    modifier = Modifier.clickable { onSignInClick() }
                )
            }
        }
    }
}
