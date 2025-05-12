@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.myapplication.ui.provider


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.components.SignUpSectionCard
import com.example.myapplication.ui.theme.OffWhite
import com.example.myapplication.ui.theme.SafetyOrange
import com.example.myapplication.ui.theme.MutedGrey
import com.example.myapplication.R
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

//import com.example.myapplication.ui.provider.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavHostController,
    onLogout: () -> Unit, // Navigation callback
    onDeleteAccount: () -> Unit, // Navigation callback
    onSaveChanges: () -> Unit // Navigation callback (optional)
) {
    // Mock user data for preview
    val mockUser = remember {
        object {
            val fullName = "John Doe"
            val email = "john@example.com"
            val location = "New York"
            val phoneNumber = "+1234567890"
            val externalLinks = "portfolio.com/john"
            val category = "Plumbing"
            val experienceLevel = "5 years"
            val hourlyRate = "$50/hr"
            val services = "Pipe repair, Installation"
            val certifications = "Certified Plumber"
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.profile),
                            contentDescription = "Profile Picture",
                            modifier = Modifier.clip(CircleShape).size(50.dp),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Text(text = mockUser.fullName, style = MaterialTheme.typography.titleLarge)
                            Text(text = mockUser.email, style = MaterialTheme.typography.bodyMedium)
                            Text(text = mockUser.location, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                },
                modifier = Modifier.padding(top = 30.dp).height(150.dp),
                colors = TopAppBarDefaults.topAppBarColors(containerColor = OffWhite)
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            item {
                var expandedBasic by remember { mutableStateOf(false) }
                SignUpSectionCard(
                    "Basic Information", listOf(
                        Triple("Full name", mockUser.fullName) { _ -> },
                        Triple("Email", mockUser.email) { _ -> },
                        Triple("Phone Number", mockUser.phoneNumber) { _ -> },
                        Triple("Location", mockUser.location) { _ -> },
                        Triple("External links", mockUser.externalLinks) { _ -> }
                    ),
                    expanded = expandedBasic,
                    onExpandToggle = { expandedBasic = !expandedBasic }
                )
            }
            // ... (Repeat for other sections with mock data) ...

            item {
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { onSaveChanges() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = SafetyOrange)
                ) {
                    Text("Save Changes", color = OffWhite)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = { onLogout() }) {
                        Text("Logout", color = MutedGrey, fontSize = 14.sp)
                    }
                    TextButton(onClick = { onDeleteAccount() }) {
                        Text("Delete Account", color = MaterialTheme.colorScheme.error, fontSize = 14.sp)
                    }
                }
            }
        }
    }
}

// Preview function
@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    val fakeNavController = rememberNavController() // Create a mock NavController
    ProfileScreen(
        navController = fakeNavController, // Pass the mock NavController
        onLogout = { println("Navigate to login") },
        onDeleteAccount = { println("Navigate to confirmation") },
        onSaveChanges = { println("Save changes") }
    )
}
