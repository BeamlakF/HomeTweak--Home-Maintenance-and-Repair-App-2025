package com.example.myapplication.ui.reusables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.model.Booking

@Composable
fun ActiveBookingCard(
    booking: Booking,
    onRescheduleClick: (Booking) -> Unit,
    onCancelClick: (Booking) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEEE3)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = booking.serviceType,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold, fontSize = 18.sp
                ),
                color = Color(0xFF04285E)
            )
            Spacer(modifier = Modifier.height(8.dp))
            InfoRow("Service Date", booking.serviceDate)
            InfoRow("Booked On", booking.bookingDate)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { onRescheduleClick(booking) },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6700)),
                    modifier = Modifier.weight(1f)
                ) { Text("Reschedule") }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { onCancelClick(booking) },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4F5255)),
                    modifier = Modifier.weight(1f)
                ) { Text("Cancel") }
            }
        }
    }
}

@Composable
fun PendingBookingCard(booking: Booking, onCancelClick: (Booking) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEEE3)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = booking.serviceType,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold, fontSize = 18.sp
                ),
                color = Color(0xFF04285E)
            )
            Spacer(modifier = Modifier.height(8.dp))
            InfoRow("Service Date", booking.serviceDate)
            InfoRow("Booked On", booking.bookingDate)
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { onCancelClick(booking) },
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4F5255))
                ) { Text("Cancel") }
            }
        }
    }
}

@Composable
fun CompletedBookingCard(booking: Booking) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEEE3)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = booking.serviceType,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold, fontSize = 18.sp
                ),
                color = Color(0xFF04285E)
            )
            Spacer(modifier = Modifier.height(8.dp))
            InfoRow("Service Date", booking.serviceDate)
            InfoRow("Booked On", booking.bookingDate)
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium, color = Color(0xFF4F5255))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
            color = Color(0xFF4F5255)
        )
    }
}
