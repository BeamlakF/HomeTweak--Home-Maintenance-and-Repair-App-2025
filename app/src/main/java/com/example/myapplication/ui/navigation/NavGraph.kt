package com.example.myapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.common.SignUpScreen
import com.example.myapplication.ui.common.LoginScreen
import com.example.myapplication.ui.customer.HomePage
import com.example.myapplication.ui.customer.AccountScreen
import com.example.myapplication.ui.common.LandingPage
import com.example.myapplication.ui.customer.MyBookingsScreen
import com.example.myapplication.ui.customer.CategoryPage
import com.example.myapplication.ui.provider.DashboardScreen
import com.example.myapplication.ui.provider.FinishSignUpScreen // Import the FinishSignUpScreen
import androidx.compose.ui.graphics.Color

object Routes {
    const val LANDING = "landing"
    const val SIGN_UP = "signup"
    const val LOGIN = "login"
    const val CUSTOMER_HOME = "customer_home"
    const val CATEGORY_PAGE = "category_page"
    const val CUSTOMER_BOOKINGS = "customer_bookings"
    const val CUSTOMER_ACCOUNT = "customer_account"
    const val PROVIDER_DASHBOARD = "provider_dashboard"
    // Remove PROVIDER_EDIT_PROFILE
    const val FINISH_PROVIDER_SIGNUP = "finish_provider_signup"
}

@Composable
fun rememberAppNavController(): NavHostController {
    return rememberNavController()
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    val SafetyOrange = Color(0xFFFF6700)
    val OffWhite = Color(0xFFFFF9F5)
    val DarkBlue = Color(0xFF04285E)
    val MutedGrey = Color(0xFF4F5255)
    val CardColor = Color(0xFFFFEEE3)
    NavHost(navController = navController, startDestination = Routes.LANDING) {
        composable(route = Routes.LANDING) {
            LandingPage(navController = navController)
        }
        composable(route = Routes.SIGN_UP) {
            SignUpScreen(navController = navController, onSignInClick = {
                navController.navigate(Routes.LOGIN)
            })
        }
        composable(route = Routes.LOGIN) {
            LoginScreen(navController = navController)
        }
        composable(route = Routes.CUSTOMER_HOME) {
            HomePage(navController = navController)
        }
        composable(route = Routes.CATEGORY_PAGE) {
            CategoryPage(navController = navController)
        }
        composable(route = Routes.CUSTOMER_BOOKINGS) {
            MyBookingsScreen(navController = navController)
        }
        composable(route = Routes.CUSTOMER_ACCOUNT) {
            AccountScreen(navController = navController)
        }
        composable(route = Routes.PROVIDER_DASHBOARD) {
            DashboardScreen(navController = navController)
        }
        // Remove PROVIDER_EDIT_PROFILE Composable
        composable(route = Routes.FINISH_PROVIDER_SIGNUP) {
            FinishSignUpScreen(
                navController = navController,
                onSaveChanges = {
                    navController.navigate(Routes.PROVIDER_DASHBOARD) {
                        popUpTo(Routes.FINISH_PROVIDER_SIGNUP) { inclusive = true }
                    }
                },
                onLogout = {
                    navController.navigate(Routes.LANDING) {
                        popUpTo(Routes.FINISH_PROVIDER_SIGNUP) { inclusive = true }
                    }
                },
                onDeleteAccount = {  // Add onDeleteAccount
                    navController.navigate(Routes.LANDING) {
                        popUpTo(Routes.FINISH_PROVIDER_SIGNUP) { inclusive = true }
                    }
                }
            )
        }
    }
}

