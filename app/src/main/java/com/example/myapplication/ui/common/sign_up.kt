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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.navigation.Routes
import com.example.myapplication.ui.theme.DarkBlue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navController: NavHostController,
    onSignInClick: () -> Unit,
    // viewModel: SignupViewModel = viewModel() // Uncomment if you have a ViewModel
) {
    // val coroutineScope = rememberCoroutineScope() // Uncomment if using coroutines with ViewModel
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val roleOptions = listOf("customer", "serviceProvider")
    var selectedRole by remember { mutableStateOf("") }
    // var errorMessage by remember { mutableStateOf<String?>(null) } // For error handling
    // var successMessage by remember { mutableStateOf<String?>(null) } // For success messages

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
                painter = painterResource(id = R.drawable.home_tweak),
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
                ),
                textStyle = TextStyle(color = DarkBlue,  fontSize = 20.sp)
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
                ),
                textStyle = TextStyle(color = DarkBlue,  fontSize = 20.sp)
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
                    ),
                    textStyle = TextStyle(color = DarkBlue) // Apply text color here
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
                    println("Email: $email, Password: $password, Role: $selectedRole")

                    val userRole = selectedRole

                    if (userRole == "customer") {
                        navController.navigate(Routes.CUSTOMER_HOME) {
                            popUpTo(Routes.SIGN_UP) { inclusive = true }
                        }
                    } else if (userRole == "serviceProvider") {
                        navController.navigate(Routes.PROVIDER_DASHBOARD) {
                            popUpTo(Routes.SIGN_UP) { inclusive = true }
                        }
                    } else {
                        println("Error: No role selected")
                        // Consider showing a user-facing error message here
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
            // Uncomment if you have error/success message display logic
            /*
            errorMessage?.let { Text(it, color = Color.Red) }
            successMessage?.let { Text(it, color = Color.Green) }
            */

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

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    val navController = rememberNavController()
    SignUpScreen(
        navController = navController,
        onSignInClick = { /* Preview doesn't need real implementation */ }
    )
}