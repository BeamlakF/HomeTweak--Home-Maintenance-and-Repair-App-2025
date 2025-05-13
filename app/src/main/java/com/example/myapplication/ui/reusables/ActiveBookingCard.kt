package com.example.myapplication.ui.reusables


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.model.Booking

@Composable
fun ActiveBookingCard(
    booking: Booking,
    onRescheduleClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFDFF0D8))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Service: ${booking.serviceType}")
            Text("Provider: ${booking.providerId}")
            Text("Time: ${booking.bookingDate}")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onRescheduleClick) {
                    Text("Reschedule")
                }
                TextButton(onClick = onCancelClick) {
                    Text("Cancel")
                }
            }
        }
    }
}
