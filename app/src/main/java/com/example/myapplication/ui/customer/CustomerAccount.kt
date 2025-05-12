package com.example.myapplication.ui.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.ui.components.FooterSection
import com.example.myapplication.ui.navigation.Routes
import com.example.myapplication.ui.theme.CardColor
import com.example.myapplication.ui.theme.DarkBlue
import com.example.myapplication.ui.theme.MutedGrey
import com.example.myapplication.ui.theme.OffWhite
import com.example.myapplication.ui.theme.SafetyOrange
import androidx.compose.material3.Button as Button1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(navController: NavHostController) {
    var isEditing by remember { mutableStateOf(false) }
    var firstName by remember { mutableStateOf("John") }
    var lastName by remember { mutableStateOf("Doe") }
    var email by remember { mutableStateOf("john.doe@example.com") }
    var password by remember { mutableStateOf("********") }
    var phoneNumber by remember { mutableStateOf("090648****") }
    var location by remember { mutableStateOf("Addis Ababa") }

    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = OffWhite,
        topBar = {
            TopAppBar(
                title = { Text("Account", color = DarkBlue) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = OffWhite),
                actions = {
                    if (!isEditing) {
                        IconButton(onClick = { isEditing = true }) {
                            Icon(Icons.Filled.Edit, contentDescription = "Edit Profile", tint = SafetyOrange)
                        }
                    }
                }
            )
        },
        bottomBar = { FooterSection(navController = navController) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text("My Profile", style = MaterialTheme.typography.headlineSmall.copy(color = DarkBlue))
                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = CardColor),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        OutlinedTextField(
                            value = firstName,
                            onValueChange = { if (isEditing) firstName = it },
                            label = { Text("First Name (Optional)", color = MutedGrey) },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = isEditing,
                            textStyle = TextStyle(color = DarkBlue)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = lastName,
                            onValueChange = { if (isEditing) lastName = it },
                            label = { Text("Last Name (Optional)", color = MutedGrey) },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = isEditing,
                            textStyle = TextStyle(color = DarkBlue)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = email,
                            onValueChange = { if (isEditing) email = it },
                            label = { Text("Email", color = MutedGrey) },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = isEditing,
                            textStyle = TextStyle(color = DarkBlue)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = if (isEditing) password else "*".repeat(password.length),
                            onValueChange = { if (isEditing) password = it },
                            label = { Text("Password", color = MutedGrey) },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = isEditing,
                            textStyle = TextStyle(color = DarkBlue),
                            visualTransformation = if (isEditing) VisualTransformation.None else PasswordVisualTransformation()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = phoneNumber,
                            onValueChange = { if (isEditing) phoneNumber = it },
                            label = { Text("Phone Number (Optional)", color = MutedGrey) },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = isEditing,
                            textStyle = TextStyle(color = DarkBlue)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = location,
                            onValueChange = { if (isEditing) location = it },
                            label = { Text("Location (Optional)", color = MutedGrey) },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = isEditing,
                            textStyle = TextStyle(color = DarkBlue)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (isEditing) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button1(
                            onClick = {
                                // TODO: Implement save changes logic with all fields
                                isEditing = false
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SafetyOrange, contentColor = OffWhite),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Filled.Save, contentDescription = "Save Changes", modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Save Changes", fontSize = 14.sp)
                        }
                        OutlinedButton(
                            onClick = { isEditing = false },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cancel", color = DarkBlue)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                } else {
                    Button1(
                        onClick = {
                            // TODO: Implement logout logic
                            // This should typically clear user session and navigate to the login/sign-up screen
                            navController.navigate(Routes.LANDING) {
                                popUpTo(Routes.CUSTOMER_ACCOUNT) { inclusive = true }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray, contentColor = OffWhite)
                    ) {
                        Icon(Icons.Filled.ExitToApp, contentDescription = "Logout", modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Logout")
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Button1(
                        onClick = { showDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red.copy(alpha = 0.8f), contentColor = OffWhite)
                    ) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete Account", modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Delete Account")
                    }
                }
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Confirm Delete") },
                text = { Text("Are you sure you want to delete your account? This action cannot be undone.") },
                confirmButton = {
                    Button1(
                        onClick = {
                            showDialog = false
                            // TODO: Implement actual delete account logic here
                            navController.navigate(Routes.LANDING) {
                                popUpTo(Routes.CUSTOMER_ACCOUNT) { inclusive = true }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    OutlinedButton(onClick = { showDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}