@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.myapplication.ui.provider

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.components.SignUpSectionCard
import com.example.myapplication.ui.theme.MutedGrey
import com.example.myapplication.ui.theme.OffWhite
import com.example.myapplication.ui.theme.SafetyOrange
import com.example.myapplication.ui.theme.DarkBlue
import com.example.myapplication.ui.theme.CardColor
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinishSignUpScreen(
    navController: NavHostController,
    onSaveChanges: () -> Unit,
    onLogout: () -> Unit,
    onDeleteAccount: () -> Unit
) {
    // Mock UI state
    var basicExpanded by remember { mutableStateOf(false) }
    var professionalExpanded by remember { mutableStateOf(false) }
    var servicesExpanded by remember { mutableStateOf(false) }
    var certificationsExpanded by remember { mutableStateOf(false) }

    // Mock form data
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var profession by remember { mutableStateOf("") }
    var service1 by remember { mutableStateOf("") }
    var service2 by remember { mutableStateOf("") }
    var certification1 by remember { mutableStateOf("") }
    var license by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(start = 20.dp)
                    ) {
                        Text(
                            text = "Finish Signing Up",
                            style = MaterialTheme.typography.titleLarge,
                            color = DarkBlue // From theme
                        )
                    }
                },
                modifier = Modifier
                    .height(70.dp)
                    .shadow(5.dp),
                colors = TopAppBarDefaults.topAppBarColors(containerColor = OffWhite), // From theme
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 40.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally // Center the buttons
        ) {
            item {
                SignUpSectionCard(
                    title = "Basic Information",
                    expanded = basicExpanded,
                    onExpandToggle = { basicExpanded = !basicExpanded },
                    fields = listOf(
                        Triple("Full Name", name) { name = it },
                        Triple("Email", email) { email = it },
                        Triple("Phone", phone) { phone = it }
                    ),
                )
            }

            item {
                SignUpSectionCard(
                    title = "Professional Details",
                    expanded = professionalExpanded,
                    onExpandToggle = { professionalExpanded = !professionalExpanded },
                    fields = listOf(
                        Triple("Profession", profession) { profession = it }
                    ),
                )
            }

            item {
                SignUpSectionCard(
                    title = "Services Provided",
                    expanded = servicesExpanded,
                    onExpandToggle = { servicesExpanded = !servicesExpanded },
                    fields = listOf(
                        Triple("Service 1", service1) { service1 = it },
                        Triple("Service 2", service2) { service2 = it }
                    ),
                )
            }

            item {
                SignUpSectionCard(
                    title = "Certifications / Licenses",
                    expanded = certificationsExpanded,
                    onExpandToggle = { certificationsExpanded = !certificationsExpanded },
                    fields = listOf(
                        Triple("Certification1", certification1) { certification1 = it },
                        Triple("License", license) { license = it }
                    ),
                )
            }

            item {
                Button(
                    onClick = {
                        // Validate fields if needed
                        if (name.isNotBlank() && email.isNotBlank()) {
                            onSaveChanges()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                        .width(200.dp), // same width as Delete Account
                    colors = ButtonDefaults.buttonColors(containerColor = SafetyOrange) // From theme
                ) {
                    Text("Save changes", color = OffWhite) // From theme
                }
            }

            item {
                //  buttons vertically stacked
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally, // Center the buttons in the column
                    verticalArrangement = Arrangement.spacedBy(8.dp) // Space between buttons

                ) {
                    Button(
                        onClick = { onLogout() },
                        colors = ButtonDefaults.buttonColors(containerColor = CardColor), // set the color
                        modifier = Modifier.width(200.dp) // same width as Delete Account
                    ) {
                        Text("Logout", color = MutedGrey) // From theme
                    }
                    Button(
                        onClick = { onDeleteAccount() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        modifier = Modifier.width(200.dp) // same width as Logout
                    ) {
                        Text("Delete Account", color = OffWhite) // From theme
                    }
                }
            }
        }
    }
}

//// Preview function
//@Preview(showBackground = true)
//@Composable
//fun SignUpScreenPreview() {
//    val fakeNavController = rememberNavController()
//    FinishSignUpScreen(
//        navController = fakeNavController,
//        onSaveChanges = {  },
//        onLogout = {  },
//        onDeleteAccount = { }
//    )
//}
