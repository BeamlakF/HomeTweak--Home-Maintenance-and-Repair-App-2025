package com.example.myapplication.ui.components

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource

// for connecting with the view model
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.viewmodel.BookingViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue



@Composable
fun ActiveBookingCard( // customer booking card active
    booking: Booking,
    onRescheduleClick: (Booking) -> Unit,
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
                    onClick = { onRescheduleClick(booking) },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF6700),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Reschedule")
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
