package com.example.myapplication.ui.customer

import androidx.navigation.NavHostController
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.model.Booking
import com.example.myapplication.ui.components.ActiveBookingCard
import com.example.myapplication.ui.components.CompletedBookingCard
import com.example.myapplication.ui.components.FooterSection
import com.example.myapplication.ui.components.PendingBookingCard
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.viewmodel.BookingViewModel
import com.example.myapplication.viewmodel.BookingViewModel.Companion.provideFactory
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.data.FakeBookingRepo
import com.example.myapplication.viewmodel.BookingActionState

@Composable
fun MyBookingsScreen(navController: NavHostController)  {
    val repository = FakeBookingRepo()
    val bookingViewModel: BookingViewModel = viewModel(factory = provideFactory(repository))

    val profilePicture by bookingViewModel.profilePicture.collectAsState()
    val displayedBookings by bookingViewModel.displayedBookings.collectAsState()
    val currentTab by bookingViewModel.currentTab.collectAsState()
    val bookingActionState by bookingViewModel.bookingActionState.collectAsState(initial = null)

    var showCancellationDialog by remember { mutableStateOf(false) }
    var cancelledBookingId by remember { mutableStateOf<String?>(null) }

    Scaffold(
        containerColor = Color(0xFFFFF9F5),
        bottomBar = { FooterSection(navController = navController) } // 🔹 Ensures FooterSection stays fixed at the bottom
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
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
                    text = "My Bookings",
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 30.sp),
                    color = Color(0xFF04285E),
                    modifier = Modifier.padding(30.dp)
                )
                // Profile Picture
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.White, CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            // Tabs Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFF9F5)),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TabItem(
                    "Active",
                    isSelected = currentTab == Booking.BookingStatus.ACTIVE,
                    onTabClick = { bookingViewModel.onTabSelected(Booking.BookingStatus.ACTIVE) }
                )
                TabItem(
                    "Pending",
                    isSelected = currentTab == Booking.BookingStatus.PENDING,
                    onTabClick = { bookingViewModel.onTabSelected(Booking.BookingStatus.PENDING) }
                )
                TabItem(
                    "Completed",
                    isSelected = currentTab == Booking.BookingStatus.COMPLETED,
                    onTabClick = { bookingViewModel.onTabSelected(Booking.BookingStatus.COMPLETED) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Booking List
            LazyColumn {
                items(displayedBookings) { booking ->
                    when (booking.status) {
                        Booking.BookingStatus.ACTIVE -> ActiveBookingCard(
                            booking = booking,
                            onRescheduleClick = { /* TODO: Implement Reschedule */ },
                            onCancelClick = { bookingViewModel.cancelBooking(booking.id) }
                        )
                        Booking.BookingStatus.PENDING -> PendingBookingCard(
                            booking = booking,
                            onCancelClick = { bookingViewModel.cancelBooking(booking.id) }
                        )
                        Booking.BookingStatus.COMPLETED -> CompletedBookingCard(booking = booking)
                    }
                }
            }

            LaunchedEffect(Unit) {
                println("Displayed Bookings in UI: $displayedBookings")
            }

            // Handle Cancellation Dialog
            LaunchedEffect(bookingActionState) {
                when (val state = bookingActionState) {
                    is BookingActionState.BookingCancelled -> {
                        cancelledBookingId = state.bookingId
                        showCancellationDialog = true
                    }
                    is BookingActionState.Error -> {
                        println("Error: ${state.message}")
                    }
                    else -> {}
                }
                bookingViewModel.resetBookingActionState()
            }
        }
    }
}

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
fun MyBookingsScreenPreview() {
    val navController = rememberNavController()
    MyBookingsScreen(navController = navController)
}

