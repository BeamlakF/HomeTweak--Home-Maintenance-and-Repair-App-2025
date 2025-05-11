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
import com.example.myapplication.ui.common.SignUpSectionCard
import com.example.myapplication.ui.theme.OffWhite
import com.example.myapplication.ui.theme.SafetyOrange
import com.example.myapplication.ui.theme.MutedGrey
import com.example.myapplication.R
import androidx.compose.foundation.shape.CircleShape
//import com.example.myapplication.ui.provider.UserViewModel

@Composable
fun ProfileScreen(viewModel: UserViewModel) {
    val user by viewModel.user.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.unnamed),
                            contentDescription = "Profile Picture",
                            modifier = Modifier.clip(CircleShape).size(50.dp),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Text(text = user.fullName, style = MaterialTheme.typography.titleLarge)
                            Text(text = user.email, style = MaterialTheme.typography.bodyMedium)
                            Text(text = user.location, style = MaterialTheme.typography.bodyMedium)
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
                        Triple("Full name", user.fullName) { viewModel.updateField("fullName", it) },
                        Triple("Email", user.email) { viewModel.updateField("email", it) },
                        Triple("Phone Number", user.phoneNumber) { viewModel.updateField("phoneNumber", it) },
                        Triple("Location", user.location) { viewModel.updateField("location", it) },
                        Triple("External links", user.externalLinks) { viewModel.updateField("externalLinks", it) }
                    ),
                    expanded = expandedBasic,
                    onExpandToggle = { expandedBasic = !expandedBasic }
                )
            }
            item {
                var expandedProfessional by remember { mutableStateOf(false) }
                SignUpSectionCard(
                    "Professional Details", listOf(
                        Triple("Category", user.category) { viewModel.updateField("category", it) },
                        Triple("Experience Level", user.experienceLevel) { viewModel.updateField("experienceLevel", it) },
                        Triple("Hourly Rate", user.hourlyRate) { viewModel.updateField("hourlyRate", it) }
                    ),
                    expanded = expandedProfessional,
                    onExpandToggle = { expandedProfessional = !expandedProfessional }
                )
            }
            item {
                var expandedServices by remember { mutableStateOf(false) }
                SignUpSectionCard(
                    "Services", listOf(
                        Triple("Services Provided", user.services) { viewModel.updateField("services", it) }
                    ),
                    expanded = expandedServices,
                    onExpandToggle = { expandedServices = !expandedServices }
                )
            }
            item {
                var expandedCertifications by remember { mutableStateOf(false) }
                SignUpSectionCard(
                    "Certifications / Licenses", listOf(
                        Triple("Certifications / Licenses", user.certifications) { viewModel.updateField("certifications", it) }
                    ),
                    expanded = expandedCertifications,
                    onExpandToggle = { expandedCertifications = !expandedCertifications }
                )
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { viewModel.updateUser(user) },
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
                    TextButton(onClick = { viewModel.logout() }) {
                        Text("Logout", color = MutedGrey, fontSize = 14.sp)
                    }
                    TextButton(onClick = { viewModel.deleteAccount() }) {
                        Text("Delete Account", color = MaterialTheme.colorScheme.error, fontSize = 14.sp)
                    }
                }
            }
        }
    }
}
