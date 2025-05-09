package com.example.myapplication.ui.customer

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.myapplication.ui.components.BookingCard

@Composable
fun MyBookingsScreenStaticPreview() {
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
                .fillMaxWidth(0.8f)
                .background(Color(0xFFFFF9F5)),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            var selectedTab by remember { mutableStateOf("Active") }
            TabItem("Active", isSelected = selectedTab == "Active") { selectedTab = "Active" }
            TabItem("Pending", isSelected = selectedTab == "Pending") { selectedTab = "Pending" }
            TabItem("Completed", isSelected = selectedTab == "Completed") { selectedTab = "Completed" }
        }

        Spacer(modifier = Modifier.height(16.dp))
        val sampleBooking = Booking(
            id = "preview1",
            customerId = "01",
            providerId = "30",
            serviceType = "Appliance Repair",
            serviceProvider = "Selam Worku",
            price = "600 Birr",
            dateTime = "May 10, 2025",
            status = Booking.BookingStatus.ACTIVE
        )
        BookingCard(
            booking = sampleBooking,
            onRescheduleClick = { booking ->
                println("Reschedule clicked for: ${booking.serviceType}")
            },
            onCancelClick = { booking ->
                println("Cancel clicked for: ${booking.serviceType}")})
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