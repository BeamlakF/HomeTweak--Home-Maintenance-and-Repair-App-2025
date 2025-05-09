package com.example.myapplication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.common.LoginScreen
import com.example.myapplication.ui.common.SignUpScreen

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Signup : Screen("signup")
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Signup.route) {
        composable(Screen.Login.route) {
            LoginScreen(
                onSignUpClick = { /* TODO: Handle login */ }
            )
        }
        composable(Screen.Signup.route) {
            SignUpScreen(
                onSignInClick = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }
    }
}


