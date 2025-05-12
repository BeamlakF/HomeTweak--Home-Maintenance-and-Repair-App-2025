package com.example.myapplication.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.model.Booking
import com.example.myapplication.model.Dashboard
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource

// for connecting with the view model
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.viewmodel.BookingViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.navigation.NavHostController
import com.example.myapplication.ui.navigation.Routes
import com.example.myapplication.ui.theme.CardColor
import com.example.myapplication.ui.theme.SafetyOrange
import com.example.myapplication.ui.theme.OffWhite



@Composable
fun ActiveBookingCard( // customer booking card active
    booking: Booking,
    onRescheduleClick: (Booking) -> Unit,
    onCancelClick: (Booking) -> Unit
) {
    var showRescheduleCard by remember { mutableStateOf(false) } // Added state to manage the visibility of RescheduleInputCard

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFEEE3)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Service Type
            Text(
                text = booking.serviceType,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                color = Color(0xFF04285E)
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Service Provider
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Service Provider",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF4F5255) // Gray text
                )
                Text(
                    text = booking.serviceProvider,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF4F5255),
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.height(4.dp))

            // Price
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Price",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF4F5255)
                )
                Text(
                    text = booking.price,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF4F5255),
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.height(4.dp))

            // Date & Time
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Date & Time",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF4F5255)
                )
                Text(
                    text = booking.dateTime,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF4F5255),
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { showRescheduleCard = true },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF6700),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Reschedule", fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { onCancelClick(booking) },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4F5255),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancel")
                }
            }
        }
        if (showRescheduleCard) { // Added conditional to display RescheduleInputCard when `showRescheduleCard` is true
            RescheduleInputCard(
                onDismiss = { showRescheduleCard = false }, // Added to hide the card on dismiss
                onConfirm = { newTime, location -> // Added to handle confirmation
                    showRescheduleCard = false
                    onRescheduleClick(booking.copy(dateTime = newTime, serviceProvider = location)) // Updated booking with new time and location
                }
            )
        }

    }
}

@Composable
fun PendingBookingCard( // customer booking card pending
    booking: Booking,
    onCancelClick: (Booking) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFEEE3)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            // Service Type
            Text(
                text = booking.serviceType,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                color = Color(0xFF04285E)
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Service Provider
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Service Provider",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF4F5255) // Gray text
                )
                Text(
                    text = booking.serviceProvider,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF4F5255),
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.height(4.dp))

            // Date & Time
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Date & Time",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF4F5255)
                )
                Text(
                    text = booking.dateTime,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF4F5255),
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { onCancelClick(booking) },
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4F5255),
                        contentColor = Color.White
                    )
                ) {
                    Text("Cancel")
                }
            }
        }
    }
}

@Composable
fun CompletedBookingCard( //customer completed booking card
    booking: Booking
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFEEE3)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Service Type
            Text(
                text = booking.serviceType,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                color = Color(0xFF04285E) // Dark blue text
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Service Provider
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Service Provider",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF4F5255) // Gray text
                )
                Text(
                    text = booking.serviceProvider,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF4F5255),
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.height(4.dp))

            // Price
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Price",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF4F5255)
                )
                Text(
                    text = booking.price,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF4F5255),
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.height(4.dp))

            // Date & Time
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Date & Time",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF4F5255)
                )
                Text(
                    text = booking.dateTime,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF4F5255),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

// Dashboard cards

@Composable
fun UserProfileRatingCard(
    userProfile: Dashboard.UserProfile,
    onEditProfileClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEEE3))
    ) {
        Row(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.defaultprofile),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(text = userProfile.name, fontWeight = FontWeight.Bold, color = Color(0xFF04285E))
                Text(text = userProfile.profession, fontSize = 12.sp, color = Color.Gray)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Rating:", color = Color(0xFF04285E))
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(Icons.Filled.Star, contentDescription = "Rating", tint = Color(0xFFFFC107), modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = userProfile.rating.toString(), fontSize = 12.sp, color = Color(0xFF04285E))
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(start = 20.dp, top = 2.dp, end = 1.dp, bottom = 9.dp)
        )  {
            Button(
                onClick = onEditProfileClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6700))
            ) {
                Text("Edit profile", color = Color(0xFFFFFFFF))
            }
        }

    }
}

@Composable
fun BookingSummaryCard(
    bookingSummary: Dashboard.BookingSummary
) {
    Card(modifier = Modifier
        .padding(8.dp)
        .width(120.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEEE3))
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = "Total", fontWeight = FontWeight.Bold, color = Color.Gray, fontSize = 12.sp)
            Text(text = bookingSummary.totalBookings.toString(), fontWeight = FontWeight.Bold, fontSize = 16.sp , color = Color(0xFF04285E))
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Completed", fontWeight = FontWeight.Bold, color = Color.Gray, fontSize = 12.sp)
            Text(text = bookingSummary.completedBookings.toString(), fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF04285E))
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Earned", fontWeight = FontWeight.Bold, color = Color.Gray, fontSize = 12.sp)
            Text(text = "$${bookingSummary.totalEarnings}", fontWeight = FontWeight.Bold, color = Color(0xFF04285E), fontSize = 16.sp)
        }
    }
}

@Composable
fun PendingBookingCardDetailsHidden(
    booking: Dashboard.Booked,
    onViewDetailsClick: (Dashboard.Booked) -> Unit
) {
    Card(
        modifier = Modifier.padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFEEE3)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f, fill = false),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Customer Profile Picture",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = booking.serviceTitle,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF04285E)
                    )
                    Text(text = booking.customerName, fontSize = 12.sp, color = Color.Gray)
                    Text(text = booking.dateTime, fontSize = 12.sp, color = Color.Gray)
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { onViewDetailsClick(booking) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6700)),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.wrapContentWidth(), // Ensure button doesn't shrink
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text("View Details", color = Color.White, fontSize = 10.sp, softWrap = true)
                Icon(
                    Icons.Filled.ArrowDropDown,
                    contentDescription = "View Details",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
fun PendingBookingCardDetailsShown(
    booking: Dashboard.Booked,
    onConfirmClick: () -> Unit,
    onDeclineClick: () -> Unit,
    onHideDetailsClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEEE3))
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f, fill = false),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = "Customer Profile Picture",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Increased spacing
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(
                            text = booking.serviceTitle,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF04285E)
                        )
                        Text(text = booking.customerName, fontSize = 12.sp, color = Color.Gray)
                        if (!booking.customerEmail.isNullOrEmpty()) {
                            Text(text = booking.customerEmail, fontSize = 12.sp, color = Color(0xFF04285E))
                        }
                        Text(text = booking.dateTime, fontSize = 12.sp, color = Color.Gray)
                        if (!booking.location.isNullOrEmpty()) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Filled.LocationOn,
                                    contentDescription = "Location",
                                    tint = Color(0xFF04285E),
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = booking.location, fontSize = 12.sp, color = Color.Gray)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = onHideDetailsClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6700)),
                    modifier = Modifier.height(36.dp).wrapContentWidth(),
                ) {
                    Row() {
                        Text("Hide", color = Color.White, softWrap = true)
                        Spacer(modifier = Modifier.width(2.dp))
                        Icon(Icons.Filled.KeyboardArrowUp, contentDescription = "Hide", tint = Color.White)
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onConfirmClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6700)),
                    modifier = Modifier.wrapContentWidth()
                ) {
                    Text("Confirm", color = Color.White, softWrap = true)
                }
                Button(
                    onClick = onDeclineClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF757575)),
                    modifier = Modifier.wrapContentWidth() // Size to content
                ) {
                    Text("Decline", color = Color.White, softWrap = true)
                }
            }
        }
    }
}

@Composable
fun ActiveDashboardCard(
    booking: Dashboard.Booked
) {
    Card(
        modifier = Modifier.padding(8.dp).fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEEE3))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f, fill = false)) {
                    Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = "Customer Profile Picture",
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(text = booking.serviceTitle, fontWeight = FontWeight.Bold, color = Color(0xFF04285E))
                        Text(text = booking.customerName, fontSize = 12.sp, color = Color.Gray)
                        if (!booking.customerEmail.isNullOrEmpty()) {
                            Text(text = booking.customerEmail, fontSize = 12.sp, color = Color(0xFF04285E))
                        }
                        Text(text = booking.dateTime, fontSize = 12.sp, color = Color.Gray)
                        if (!booking.location.isNullOrEmpty()) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Filled.LocationOn, contentDescription = "Location", tint = Color(0xFF04285E), modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = booking.location, fontSize = 12.sp, color = Color.Gray)
                            }
                        }
                    }
                }
//
            }
//
            Text(
                text = booking.status.toString(),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFF2E7D32),
                modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.End)
            )
        }
    }
}


@Composable
fun CompleteDashboardCard(
    booking: Dashboard.Booked
) {
    Card(
        modifier = Modifier.padding(8.dp).fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEEE3))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f, fill = false)) {
                    Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = "Customer Profile Picture",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(text = booking.serviceTitle, fontWeight = FontWeight.Bold, color = Color(0xFF04285E))
                        Text(text = booking.customerName, fontSize = 12.sp, color = Color.Gray)
                        if (!booking.customerEmail.isNullOrEmpty()) {
                            Text(text = booking.customerEmail, fontSize = 12.sp, color = Color(0xFF04285E))
                        }
                        Text(text = booking.dateTime, fontSize = 12.sp, color = Color.Gray)
                        if (!booking.location.isNullOrEmpty()) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Filled.LocationOn, contentDescription = "Location", tint = Color(0xFF04285E), modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = booking.location, fontSize = 12.sp, color = Color.Gray)
                            }
                        }
                    }
                }

            }
            if (!booking.price.isNullOrEmpty()) {
                Text(
                    text = "+${booking.price}",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFF2E7D32), // Green color
                    modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.End)
                )
            }
        }
    }
}

//for the booking and rescheduling
@Composable
fun BookingInputCard(
    onDismiss: () -> Unit,
    onConfirm: (date: String, time: String, location: String) -> Unit
) {
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEEE3)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Book a New Appointment", style = MaterialTheme.typography.titleLarge, color = Color(0xFF04285E))
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = date,
                onValueChange = { date = it },
                label = { Text("Date", color = Color(0xFF4F5255)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = time,
                onValueChange = { time = it },
                label = { Text("Time", color = Color(0xFF4F5255)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Location", color = Color(0xFF4F5255)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(onClick = onDismiss) {
                    Text("Cancel", color = Color(0xFFff6700))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { onConfirm(date, time, location) }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFff6700))) {
                    Text("Book", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun RescheduleInputCard(
    onDismiss: () -> Unit,
    onConfirm: (newTime: String, location: String) -> Unit
) {
    var newTime by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEEE3)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Reschedule Booking", style = MaterialTheme.typography.titleLarge, color = Color(0xFF04285E))
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = newTime,
                onValueChange = { newTime = it },
                label = { Text("New Time", color = Color(0xFF4F5255)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("New Location", color = Color(0xFF4F5255)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(onClick = onDismiss) {
                    Text("Cancel", color = Color(0xFFff6700))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { onConfirm(newTime, location) }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFff6700))) {
                    Text("Update Booking", color = Color.White)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookingInputCardPreview() {
    BookingInputCard(
        onDismiss = {},
        onConfirm = { _, _, _ -> }
    )
}

@Preview(showBackground = true)
@Composable
fun RescheduleInputCardPreview() {
    RescheduleInputCard(
        onDismiss = {},
        onConfirm = { _, _ -> }
    )
}

//card on signup
@Composable
fun SignUpSectionCard(
    title: String,
    fields: List<Triple<String, String, (String) -> Unit>>,
    expanded: Boolean,
    onExpandToggle: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .animateContentSize(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onExpandToggle) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = null
                    )
                }
            }
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                for ((label, value, onValueChange) in fields) {
                    OutlinedTextField(
                        value = value,
                        onValueChange = onValueChange,
                        label = {
                            Text(
                                text = label,
                                style = MaterialTheme.typography.bodySmall // Adjusted this to correctly apply style
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        colors = TextFieldDefaults.colors(),
                        shape = MaterialTheme.shapes.medium
                    )
                }
            }
        }
    }
}
@Composable
fun FooterSection(navController: NavHostController) {
    Surface(
        color = CardColor,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = { navController.navigate(Routes.CUSTOMER_HOME) }) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Home",
                    tint = SafetyOrange,
                )
            }

            IconButton(onClick = { navController.navigate(Routes.CUSTOMER_BOOKINGS) }) {
                Icon(
                    imageVector = Icons.Filled.CalendarMonth,
                    contentDescription = "Calendar",
                    tint = SafetyOrange,
                )
            }

            IconButton(onClick = { navController.navigate(Routes.CUSTOMER_ACCOUNT) }) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Profile",
                    tint = SafetyOrange,
                )
            }
        }
    }
}
