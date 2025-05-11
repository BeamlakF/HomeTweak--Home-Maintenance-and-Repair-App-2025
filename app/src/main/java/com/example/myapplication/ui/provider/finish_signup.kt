@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.myapplication.ui.provider

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.common.SignUpSectionCard
import com.example.myapplication.ui.theme.MutedGrey
import com.example.myapplication.ui.theme.OffWhite
import com.example.myapplication.ui.provider.FinishSignUpViewModel

@Composable
fun SignUpScreen(viewModel: FinishSignUpViewModel) {
    val uiState by viewModel.uiState.collectAsState()

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
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                },
                modifier = Modifier
                    .height(70.dp)
                    .shadow(5.dp),
                colors = TopAppBarDefaults.topAppBarColors(containerColor = OffWhite),
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 40.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                SignUpSectionCard(
                    title = "Basic Information",
                    expanded = uiState.basicExpanded,
                    onExpandToggle = { viewModel.toggleSection("basic") },
                    fields = listOf(
                        Triple("Full Name", uiState.name) { viewModel.updateField("name", it) },
                        Triple("Email", uiState.email) { viewModel.updateField("email", it) },
                        Triple("Phone", uiState.phone) { viewModel.updateField("phone", it) }
                    )
                )
            }

            item {
                SignUpSectionCard(
                    title = "Professional Details",
                    expanded = uiState.professionalExpanded,
                    onExpandToggle = { viewModel.toggleSection("professional") },
                    fields = listOf(
                        Triple("Profession", uiState.profession) { viewModel.updateField("profession", it) }
                    )
                )
            }

            item {
                SignUpSectionCard(
                    title = "Services Provided",
                    expanded = uiState.servicesExpanded,
                    onExpandToggle = { viewModel.toggleSection("services") },
                    fields = listOf(
                        Triple("Service 1", uiState.service1) { viewModel.updateField("service1", it) },
                        Triple("Service 2", uiState.service2) { viewModel.updateField("service2", it) }
                    )
                )
            }

            item {
                SignUpSectionCard(
                    title = "Certifications / Licenses",
                    expanded = uiState.certificationsExpanded,
                    onExpandToggle = { viewModel.toggleSection("certifications") },
                    fields = listOf(
                        Triple("Certification1", uiState.certification1) { viewModel.updateField("certification1", it) },
                        Triple("License", uiState.license) { viewModel.updateField("license", it) }
                    )
                )
            }

            item {
                Button(
                    onClick = { viewModel.submit() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                ) {
                    Text("Save changes")
                }
            }

            item {
                TextButton(
                    onClick = { viewModel.logout() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text("Logout", color = MutedGrey)
                }
            }
        }
    }
}
