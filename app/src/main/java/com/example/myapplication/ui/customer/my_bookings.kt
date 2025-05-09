package com.example.myapplication.ui.customer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown

import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hometweak.Booking
import com.example.hometweak.BookingRepository
import com.example.hometweak.ui.theme.CardBackground
import com.example.hometweak.ui.theme.LightBackground

enum class BookingStatus { ACTIVE, PENDING, COMPLETED }

@Preview

@Composable
fun MyBookingsScreen() {
    var selectedTab by remember { mutableStateOf(BookingStatus.ACTIVE) }
    var showDialog by remember { mutableStateOf(false) }
    var currentBooking by remember { mutableStateOf<Booking?>(null) }
    val activeBookings by remember { mutableStateOf(BookingRepository.activeBookings) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBackground)
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "My Bookings",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile",
                modifier = Modifier.size(40.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tabs
        TabRow(
            selectedTabIndex = selectedTab.ordinal,
            containerColor = Color.Transparent,
            contentColor = Color.Black,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTab.ordinal]),
                    height = 2.dp,
                    color = Color(0xFFDE6A1F)
                )
            }
        ) {
            BookingStatus.values().forEach { status ->
                Tab(
                    selected = selectedTab == status,
                    onClick = { selectedTab = status },
                    text = {
                        Text(
                            text = status.name.capitalize(),
                            color = if (selectedTab == status) Color.Black else Color.Gray
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Content based on selected tab
        when (selectedTab) {
            BookingStatus.ACTIVE -> ActiveBookings(
                onBookingClick = { booking ->
                    currentBooking = booking
                    showDialog = true
                }
            )
            BookingStatus.PENDING -> PendingBookings(
                onBookingClick = { booking ->
                    currentBooking = booking
                    showDialog = true
                }
            )
            BookingStatus.COMPLETED -> CompletedBookings(
                onBookingClick = { booking ->
                    currentBooking = booking
                    showDialog = true
                }
            )
        }

        // Details Dialog
        if (showDialog && currentBooking != null) {
            when (selectedTab) {
                BookingStatus.ACTIVE -> ActiveBookingDetailsDialog(
                    booking = currentBooking!!,
                    onDismiss = { showDialog = false }
                )
                BookingStatus.PENDING -> PendingBookingDetailsDialog(
                    booking = currentBooking!!,
                    onDismiss = { showDialog = false }
                )
                BookingStatus.COMPLETED -> CompletedBookingDetailsDialog(
                    booking = currentBooking!!,
                    onDismiss = { showDialog = false }
                )
            }
        }
    }
}



@Composable
fun ActiveBookings(onBookingClick: (Booking) -> Unit) {
    val activeBookings = BookingRepository.activeBookings

    if (activeBookings.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("No active bookings yet", color = Color.Gray)
        }
    } else {
        Column {
            activeBookings.forEach { booking ->
                BookingCard(
                    serviceName = booking.serviceName,
                    providerName = booking.providerName,
                    dateTime = booking.dateTime,
                    onClick = { onBookingClick(booking) }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }

}

@Composable
fun PendingBookings(onBookingClick: (Booking) -> Unit) {
    val pendingBookings = listOf(
        Booking(
            serviceName = "Carpentry Work",
            providerName = "Samuel Getachew",
            dateTime = "April 18, 2025. 10:00am",
            customerName = "Marta Girma",
            price = "750 Birr",
            location = "Addis Ababa, Kazanchis"
        ),
        Booking(
            serviceName = "AC Repair",
            providerName = "Michael Solomon",
            dateTime = "April 19, 2025. 3:00pm",
            customerName = "Tewodros Bekele",
            price = "600 Birr",
            location = "Addis Ababa, Sarbet"
        )
    )

    Column {
        pendingBookings.forEach { booking ->
            BookingCard(
                serviceName = booking.serviceName,
                providerName = booking.providerName,
                dateTime = booking.dateTime,
                onClick = { onBookingClick(booking) }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun CompletedBookings(onBookingClick: (Booking) -> Unit) {
    val completedBookings = listOf(
        Booking(
            serviceName = "Painting Service",
            providerName = "Helen Tadesse",
            dateTime = "March 28, 2025. 9:00am",
            customerName = "Alemayehu Kebede",
            price = "1200 Birr",
            location = "Addis Ababa, Bole"
        ),
        Booking(
            serviceName = "Electrical Wiring",
            providerName = "Daniel Mekonnen",
            dateTime = "April 5, 2025. 1:30pm",
            customerName = "Selamawit Assefa",
            price = "900 Birr",
            location = "Addis Ababa, Mekanisa"
        )
    )

    Column {
        completedBookings.forEach { booking ->
            BookingCard(
                serviceName = booking.serviceName,
                providerName = booking.providerName,
                dateTime = booking.dateTime,
                onClick = { onBookingClick(booking) }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun BookingCard(
    serviceName: String,
    providerName: String,
    dateTime: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp))
            .height(94.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = Icons.Default.Person,
                contentDescription = "User Icon",
                modifier = Modifier
                    .size(48.dp)
                    .background(Color.White, CircleShape)
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = serviceName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF04285E)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = providerName,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = dateTime,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF04285E)
                )
            }

            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFDE6A1F)
                ),
                shape = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "View Details",
                    fontSize = 12.sp,
                    color = Color.White
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Details",
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable

fun ActiveBookingDetailsDialog(booking: Booking, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        },
        title = {
            Text(
                text = booking.serviceName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                DetailRow("Customer Name", booking.customerName)
                DetailRow("Provider Name", booking.providerName)
                DetailRow("Date & Time", booking.dateTime)
                DetailRow("Price", booking.price)
                DetailRow("Location", booking.location)
            }
        }
    )
}


@Composable
fun PendingBookingDetailsDialog(booking: Booking, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = booking.serviceName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                DetailRow("Customer Name", booking.customerName)
                DetailRow("Price", booking.price)
                Spacer(modifier = Modifier.height(8.dp))
                DetailRow("Service Provider", booking.providerName)
                DetailRow("Date & Time", booking.dateTime)
                Spacer(modifier = Modifier.height(8.dp))
                DetailRow("Location", booking.location)
            }
        },
        confirmButton = {
            Button(
                onClick = { /* Handle approve */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2196F3)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Approve")
            }
        },
        dismissButton = {
            Button(
                onClick = { /* Handle cancel */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF44336)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancel Request")
            }
        },
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun CompletedBookingDetailsDialog(booking: Booking, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = booking.serviceName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                DetailRow("Customer Name", booking.customerName)
                DetailRow("Price Paid", booking.price)
                Spacer(modifier = Modifier.height(8.dp))
                DetailRow("Service Provider", booking.providerName)
                DetailRow("Completion Date", booking.dateTime)
                Spacer(modifier = Modifier.height(8.dp))
                DetailRow("Location", booking.location)
                Spacer(modifier = Modifier.height(8.dp))
                DetailRow("Status", "Completed")
                DetailRow("Rating", "4.8/5")
            }
        },
        confirmButton = {
            Button(
                onClick = { /* Handle review */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF9C27B0)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Leave Review")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF607D8B)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Close")
            }
        },
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontWeight = FontWeight.Bold)
        Text(text = value)
    }
}
