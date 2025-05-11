package com.example.myapplication.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.data.repository.UserRepositoryImpl
import com.example.myapplication.viewmodels.SignUpViewModel
import com.example.myapplication.viewmodels.SignUpViewModelFactory

@Composable
fun SignUpScreen(
    onSignInClick: () -> Unit
) {
    val context = LocalContext.current

    // Create the repository and ViewModelFactory
    val userRepository = remember { UserRepositoryImpl() }
    val viewModelFactory = remember { SignUpViewModelFactory(userRepository) }
    val signUpViewModel: SignUpViewModel = viewModel(factory = viewModelFactory)

    // Collect state from StateFlows
    val email by signUpViewModel.email.collectAsState()
    val password by signUpViewModel.password.collectAsState()
    val selectedRole by signUpViewModel.selectedRole.collectAsState()
    val expanded by signUpViewModel.expanded.collectAsState()
    val signUpResult by signUpViewModel.signUpResult.collectAsState()
    val roleOptions = signUpViewModel.roleOptions

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Sign Up", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { signUpViewModel.onEmailChange(it) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { signUpViewModel.onPasswordChange(it) },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box {
            OutlinedTextField(
                value = selectedRole,
                onValueChange = {},
                label = { Text("Role") },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { signUpViewModel.onExpandedChange(true) }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { signUpViewModel.onExpandedChange(false) }
            ) {
                roleOptions.forEach { role ->
                    DropdownMenuItem(onClick = { signUpViewModel.onRoleSelected(role) }) {
                        Text(role)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { signUpViewModel.onSignUp() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign Up")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(signUpResult, modifier = Modifier.padding(top = 16.dp))

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = onSignInClick) {
            Text("Already have an account? Sign in")
        }
    }
}


