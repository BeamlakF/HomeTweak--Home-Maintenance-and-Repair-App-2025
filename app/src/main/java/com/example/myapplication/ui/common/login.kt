package com.example.myapplication.ui.common


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.myapplication.viewmodels.LoginViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginScreen(
    onSignUpClick: () -> Unit,
    viewModel: LoginViewModel = viewModel() // Get the ViewModel
) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val rememberMe by viewModel.rememberMe.collectAsState()

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

            // Logo
            Image(
                painter = painterResource(id = R.drawable.hometweak_logo),
                contentDescription = "Logo",
                modifier = Modifier.height(150.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Title
            Text(
                "Login",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color(0xFF04285E),
                    fontSize = 36.sp
                ),
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Description
            Text(
                "Sign in to your account via email",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color(0xFF4F5255)
                ),
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Email input field
            OutlinedTextField(
                value = email,
                onValueChange = { viewModel.onEmailChange(it) },
                placeholder = { Text("Enter your email") },
                leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "Email Icon") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(4.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    unfocusedContainerColor = Color(0xFFFFF1E8)
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Password input field
            OutlinedTextField(
                value = password,
                onValueChange = { viewModel.onPasswordChange(it) },
                placeholder = { Text("Enter your password") },
                leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "Password Icon") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(4.dp),
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    unfocusedContainerColor = Color(0xFFFFF1E8)
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Remember me checkbox
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = rememberMe,
                        onCheckedChange = { viewModel.onRememberMeChange(it) },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color.Black,
                            uncheckedColor = Color(0xFF999999),
                            checkmarkColor = Color.White
                        )
                    )
                    Text(
                        "Remember me",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF4F5255),
                        fontSize = 18.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                // Forgot password text
                Text(
                    text = "Forgot password?",
                    color = Color(0xFF4F5255),
                    fontSize = 18.sp,
                    modifier = Modifier.clickable {
                        Toast.makeText(
                            null, // Replace with valid context
                            "Forgot password feature coming soon",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Login button
            Button(
                onClick = { viewModel.login() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6B00)),
                shape = RoundedCornerShape(40),
                modifier = Modifier
                    .fillMaxWidth(0.65f)
                    .height(65.dp)
            ) {
                Text("Login", fontSize = 28.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Sign up option
            Row {
                Text("Not a member?", style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color(0xFF4F5255),
                    fontSize = 16.sp
                ))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    "Create new account",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFFFF6B00),
                    fontSize = 16.sp,
                    modifier = Modifier.clickable { onSignUpClick() }
                )
            }
        }
    }
}


