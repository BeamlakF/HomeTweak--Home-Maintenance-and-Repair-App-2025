package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.myapplication.ui.theme.*

// Data Classes (Top Level)
data class ServiceProvider(
    val id: String,
    val name: String,
    val profession: String,
    val rating: Float,
    val price: String,
    val availability: String,
    val location: String,
    val reviews: List<Review>
)

data class Review(
    val text: String,
    val author: String
)

data class BookingDetails(
    val providerId: String,
    val date: String,
    val time: String,
    val location: String
)

// Main Screen Composable (Top Level)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceProviderProfileScreen(
    provider: ServiceProvider,
    onBackClick: () -> Unit,
    onBookingConfirmed: (BookingDetails) -> Unit
) {
    var showBookingCard by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { ProfileTopBar(onBackClick) },
        containerColor = OffWhite
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ProviderHeader(provider)
                DividerSection()
                ProviderDetails(provider)
                DividerSection()
                ReviewsSection(provider.reviews)
                BookNowButton { showBookingCard = true }
            }

            if (showBookingCard) {
                BookingDialog(
                    onDismiss = { showBookingCard = false },
                    onConfirm = { date, time, location ->
                        onBookingConfirmed(
                            BookingDetails(
                                provider.id,
                                date,
                                time,
                                location
                            )
                        )
                        showBookingCard = false
                    }
                )
            }
        }
    }
}

// Sub-Components (All Top Level Declarations)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileTopBar(onBackClick: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                "Service Provider",
                style = MaterialTheme.typography.titleLarge,
                color = DarkBlue
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = DarkBlue
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = OffWhite
        )
    )
}

@Composable
private fun ProviderHeader(provider: ServiceProvider) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(id = android.R.drawable.ic_menu_help),
            contentDescription = "Provider",
            modifier = Modifier.size(48.dp),
            tint = SafetyOrange
        )
        Spacer(Modifier.width(16.dp))
        Column {
            Text(
                text = provider.name,
                style = MaterialTheme.typography.headlineLarge,
                color = DarkBlue
            )
            Text(
                text = provider.profession,
                style = MaterialTheme.typography.bodyLarge,
                color = MutedGrey
            )
            RatingBar(rating = provider.rating)
        }
    }
}

@Composable
private fun DividerSection() {
    Divider(
        modifier = Modifier.padding(vertical = 16.dp),
        color = MutedGrey.copy(alpha = 0.2f)
    )
}

@Composable
private fun ProviderDetails(provider: ServiceProvider) {
    Column {
        DetailItem(Icons.Filled.Star, "Price: ${provider.price}/hour")
        Spacer(Modifier.height(8.dp))
        DetailItem(Icons.Filled.DateRange, "Availability: ${provider.availability}")
        Spacer(Modifier.height(8.dp))
        DetailItem(Icons.Filled.LocationOn, "Location: ${provider.location}")
    }
}

@Composable
private fun DetailItem(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = SafetyOrange)
        Spacer(Modifier.width(8.dp))
        Text(text, style = MaterialTheme.typography.bodyLarge, color = DarkBlue)
    }
}

@Composable
private fun ReviewsSection(reviews: List<Review>) {
    Text(
        text = "Customer Reviews",
        style = MaterialTheme.typography.titleLarge,
        color = DarkBlue,
        modifier = Modifier.padding(bottom = 8.dp)
    )
    Column {
        reviews.forEach { review ->
            ReviewItem(review)
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun ReviewItem(review: Review) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CardColor),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(
                "\"${review.text}\"",
                style = MaterialTheme.typography.bodyLarge.copy(fontStyle = FontStyle.Italic),
                color = DarkBlue
            )
            Text(
                "- ${review.author}",
                style = MaterialTheme.typography.bodySmall,
                color = MutedGrey,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun RatingBar(rating: Float) {
    Row {
        Text("Rating: $rating/5 ", style = MaterialTheme.typography.bodyLarge, color = DarkBlue)
        repeat(5) { index ->
            Icon(
                Icons.Filled.Star,
                contentDescription = null,
                tint = if (index < rating) SafetyOrange else MutedGrey.copy(alpha = 0.3f),
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
private fun BookNowButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = SafetyOrange
        )
    ) {
        Text("BOOK NOW", style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
private fun BookingDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, String) -> Unit
) {
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = CardColor),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    "Book Appointment",
                    style = MaterialTheme.typography.titleLarge,
                    color = DarkBlue
                )
                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Date", color = MutedGrey) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SafetyOrange,
                        unfocusedBorderColor = MutedGrey.copy(alpha = 0.5f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = time,
                    onValueChange = { time = it },
                    label = { Text("Time", color = MutedGrey) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SafetyOrange,
                        unfocusedBorderColor = MutedGrey.copy(alpha = 0.5f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Location", color = MutedGrey) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SafetyOrange,
                        unfocusedBorderColor = MutedGrey.copy(alpha = 0.5f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = SafetyOrange
                        )
                    ) {
                        Text("Cancel")
                    }
                    Spacer(Modifier.width(8.dp))
                    Button(
                        onClick = { onConfirm(date, time, location) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SafetyOrange
                        )
                    ) {
                        Text("Confirm")
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun ServiceProviderProfileScreenPreview() {
    val sampleProvider = ServiceProvider(
        id = "1",
        name = "John Doe",
        profession = "Plumber",
        rating = 4.5f,
        price = "Ksh 500",
        availability = "Mon-Fri, 9am-5pm",
        location = "Nairobi",
        reviews = listOf(
            Review("Great service!", "Jane K"),
            Review("Very professional.", "Peter M"),
            Review("Arrived on time and did a fantastic job.", "Sarah L")
        )
    )
    ServiceProviderProfileScreen(
        provider = sampleProvider,
        onBackClick = {},
        onBookingConfirmed = {}
    )
}
