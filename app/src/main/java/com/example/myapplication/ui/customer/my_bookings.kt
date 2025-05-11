package com.example.myapplication.ui.customer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.data.model.Booking.BookingStatus
import com.example.myapplication.ui.reusables.ActiveBookingCard
import com.example.myapplication.ui.reusables.PendingBookingCard
import com.example.myapplication.ui.reusables.CompletedBookingCard
import com.example.myapplication.viewmodels.BookingViewModel

@Composable
fun MyBookingsScreen(viewModel: BookingViewModel) {
    val profilePic by viewModel.profilePicture.collectAsState()
    val currentTab by viewModel.currentTab.collectAsState()
    val bookings by viewModel.displayedBookings.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp)
            .background(Color(0xFFFFF9F5))
    ) {
        // Header
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

            Image(
                painter = painterResource(id = profilePic ?: R.drawable.ic_launcher_foreground),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.White, CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(2.dp))

        // Tab Row Replacement
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf(
                BookingStatus.ACTIVE to "Active",
                BookingStatus.PENDING to "Pending",
                BookingStatus.COMPLETED to "Completed"
            ).forEach { (status, label) ->
                Text(
                    text = label,
                    color = if (currentTab == status) Color(0xFFFF6700) else Color(0xFF4F5255),
                    fontSize = 16.sp,
                    modifier = Modifier
                        .clickable { viewModel.onTabSelected(status) }
                        .padding(8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Booking List
        bookings.forEach { booking ->
            when (booking.status) {
                BookingStatus.ACTIVE -> {
                    ActiveBookingCard(
                        booking = booking,
                        onRescheduleClick = { selected ->
                            println("Reschedule clicked for: ${selected.serviceType}")
                        },
                        onCancelClick = { selected ->
                            viewModel.cancelBooking(selected.id)
                        }
                    )
                }

                BookingStatus.PENDING -> {
                    PendingBookingCard(
                        booking = booking,
                        onCancelClick = { selected ->
                            viewModel.cancelBooking(selected.id)
                        }
                    )
                }

                BookingStatus.COMPLETED -> {
                    CompletedBookingCard(booking = booking)
                }
            }
        }
    }
}
