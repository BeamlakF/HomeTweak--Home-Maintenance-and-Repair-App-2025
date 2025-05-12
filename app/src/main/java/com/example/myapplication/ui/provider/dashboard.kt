package com.example.myapplication.ui.provider

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController // Import NavHostController
import com.example.myapplication.R
import com.example.myapplication.data.FakeDashboardRepo
import com.example.myapplication.model.Dashboard
import com.example.myapplication.ui.components.ActiveDashboardCard
import com.example.myapplication.ui.components.BookingSummaryCard
import com.example.myapplication.ui.components.CompleteDashboardCard
import com.example.myapplication.ui.components.PendingBookingCardDetailsHidden
import com.example.myapplication.ui.components.PendingBookingCardDetailsShown
import com.example.myapplication.ui.components.UserProfileRatingCard
import com.example.myapplication.ui.navigation.Routes // Import Routes
import com.example.myapplication.viewmodel.DashboardViewModel
import com.example.myapplication.viewmodel.DashboardViewModel.Companion.provideFactory
import androidx.navigation.compose.rememberNavController


@Composable
fun DashboardScreen(navController: NavHostController) { // Accept NavHostController
    val dashboardRepo = remember { FakeDashboardRepo() }
    val dashboardViewModel: DashboardViewModel = viewModel(factory = provideFactory(dashboardRepo))
    val profile by dashboardViewModel.profile.collectAsState()
    val bookingSummary by dashboardViewModel.bookingSummary.collectAsState()
    val displayedBookings by dashboardViewModel.displayedBookings.collectAsState()
    val expandedBookings by dashboardViewModel.expandedBookings.collectAsState()
    val selectedTab by dashboardViewModel.currentTab.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp)
            .background(Color(0xFFFFF9F5))
    ) {
        // Header Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Dashboard",
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 30.sp),
                color = Color(0xFF04285E),
                modifier = Modifier.padding(15.dp)
            )
            // Profile Picture
            Image(
                painter = painterResource(id = R.drawable.defaultprofile),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.White, CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Welcome Back!",
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 26.sp),
            color = Color(0xFF04285E),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Row to hold the User Profile and Booking Summary Cards
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            profile?.let {
                UserProfileRatingCard(
                    userProfile = it,
                    onEditProfileClick = {
                        navController.navigate(Routes.FINISH_PROVIDER_SIGNUP) // Navigate to Provider Profile
                    },
                )
            }
            Spacer(modifier = Modifier.width(0.dp))
            bookingSummary?.let {
                BookingSummaryCard(
                    bookingSummary = it,
                )
            }
        }

        Text(
            text = "My Booking",
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 30.sp),
            color = Color(0xFF04285E),
            modifier = Modifier.padding(12.dp)
        )
        // Tabs Section to filter bookings
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFF9F5)),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TabItem(
                "Active",
                isSelected = selectedTab == Dashboard.BookingStatus.ACTIVE
            ) { dashboardViewModel.onTabSelected(Dashboard.BookingStatus.ACTIVE) }
            TabItem(
                "Pending",
                isSelected = selectedTab == Dashboard.BookingStatus.PENDING
            ) { dashboardViewModel.onTabSelected(Dashboard.BookingStatus.PENDING) }
            TabItem(
                "Completed",
                isSelected = selectedTab == Dashboard.BookingStatus.COMPLETED
            ) { dashboardViewModel.onTabSelected(Dashboard.BookingStatus.COMPLETED) }
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(displayedBookings) { booking ->
                when (booking.status) {
                    Dashboard.BookingStatus.PENDING -> {
                        val isExpanded = expandedBookings.contains(booking.id)
                        if (isExpanded) {
                            PendingBookingCardDetailsShown(
                                booking = booking,
                                onConfirmClick = { dashboardViewModel.confirmBooking(booking.id) },
                                onDeclineClick = { dashboardViewModel.declineBooking(booking.id) },
                                onHideDetailsClick = { dashboardViewModel.toggleBookingDetails(booking.id) }
                            )
                        } else {
                            PendingBookingCardDetailsHidden(
                                booking = booking,
                                onViewDetailsClick = { dashboardViewModel.toggleBookingDetails(booking.id) }
                            )
                        }
                    }
                    Dashboard.BookingStatus.ACTIVE -> {
                        ActiveDashboardCard(booking = booking)
                    }
                    Dashboard.BookingStatus.COMPLETED -> {
                        CompleteDashboardCard(booking = booking)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

// Reusable TabItem composable
@Composable
fun TabItem(title: String, isSelected: Boolean, onTabClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .clickable { onTabClick(title) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = Color(0xFF04285E),
            style = MaterialTheme.typography.bodyMedium
        )
        // Indicator to show if the tab is selected
        if (isSelected) {
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(2.dp)
                    .background(Color(0xFFFF6700))
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    val fakeNavController = rememberNavController() // Create a mock NavController
    DashboardScreen(navController = fakeNavController)
}
