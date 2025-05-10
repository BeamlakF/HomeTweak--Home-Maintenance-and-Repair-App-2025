package com.example.myapplication.presentation.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.common.LoginScreen
import com.example.myapplication.ui.common.SignUpScreen
import com.example.myapplication.ui.common.ForgotPasswordScreen
import com.example.myapplication.ui.common.ResetPasswordScreen
import com.example.myapplication.viewmodel.ForgotPasswordViewModel

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Signup : Screen("signup")
    data object ForgotPassword : Screen("ForgotPassword")
    data object ResetPassword : Screen("ResetPassword")
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Signup.route) {
        composable(Screen.Login.route) {
            LoginScreen(
                onSignUpClick = { navController.navigate(Screen.Signup.route) },
                onForgotPasswordClick = { navController.navigate(Screen.ForgotPassword.route) }
            )
        }
        composable(Screen.Signup.route) {
            SignUpScreen(
                onSignInClick = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }
        composable(Screen.ForgotPassword.route) {

        }
        composable(Screen.ResetPassword.route) {

        }
    }
}


