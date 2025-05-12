import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

// Data Classes
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceProviderProfileScreen(
    provider: ServiceProvider,
    onBackClick: () -> Unit,
    onBookingConfirmed: (BookingDetails) -> Unit
) {
    var showBookingCard by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Service Provider") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ProviderHeader(provider = provider)
                Divider(modifier = Modifier.padding(vertical = 16.dp))
                ProviderDetails(provider = provider)
                Divider(modifier = Modifier.padding(vertical = 16.dp))
                ReviewsSection(reviews = provider.reviews)
                BookNowButton(onClick = { showBookingCard = true })
            }

            if (showBookingCard) {
                Dialog(onDismissRequest = { showBookingCard = false }) {
                    BookingInputCard(
                        onDismiss = { showBookingCard = false },
                        onConfirm = { date, time, location ->
                            onBookingConfirmed(
                                BookingDetails(
                                    providerId = provider.id,
                                    date = date,
                                    time = time,
                                    location = location
                                )
                            )
                            showBookingCard = false
                        }
                    )
                }
            }
        }
    }
}

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
                Button(
                    onClick = { onConfirm(date, time, location) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFff6700))
                ) {
                    Text("Book", color = Color.White)
                }
            }
        }
    }
}

@Composable
private fun ProviderHeader(provider: ServiceProvider) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(id = android.R.drawable.ic_menu_help), // Replace with your icon
            contentDescription = "Provider",
            modifier = Modifier.size(48.dp),
            tint = Color(0xFF4285F4)
        )
        Spacer(Modifier.width(16.dp))
        Column {
            Text(
                text = provider.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = provider.profession,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
            RatingBar(rating = provider.rating)
        }
    }
}

@Composable
private fun ProviderDetails(provider: ServiceProvider) {
    Column {
        DetailItem(icon = Icons.Filled.Star, text = "Price: ${provider.price}/hour")
        Spacer(Modifier.height(8.dp))
        DetailItem(icon = Icons.Filled.DateRange, text = "Availability: ${provider.availability}")
        Spacer(Modifier.height(8.dp))
        DetailItem(icon = Icons.Filled.LocationOn, text = "Location: ${provider.location}")
    }
}

@Composable
private fun DetailItem(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = Color(0xFF4285F4))
        Spacer(Modifier.width(8.dp))
        Text(text, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun ReviewsSection(reviews: List<Review>) {
    Text(
        text = "Customer Reviews",
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold,
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
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(12.dp)) {
            Text("\"${review.text}\"", fontStyle = FontStyle.Italic)
            Text(
                "- ${review.author}",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun RatingBar(rating: Float) {
    Row {
        Text("Rating: $rating/5 ")
        repeat(5) { index ->
            Icon(
                Icons.Filled.Star,
                contentDescription = null,
                tint = if (index < rating) Color(0xFFFFC107) else Color.LightGray,
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
            containerColor = Color(0xFF4285F4),
            contentColor = Color.White
        )
    ) {
        Text("BOOK NOW", fontWeight = FontWeight.Bold)
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ServiceProviderProfileScreenPreview() {
    MaterialTheme {
        ServiceProviderProfileScreen(
            provider = ServiceProvider(
                id = "1",
                name = "John Doe",
                profession = "Electrician",
                rating = 4.5f,
                price = "500 Birr",
                availability = "Mon-Fri, 9AM-5PM",
                location = "Addis Ababa, Bole",
                reviews = listOf(
                    Review(
                        text = "Great service! Fixed my wiring issues quickly.",
                        author = "Alice"
                    ),
                    Review(
                        text = "Professional and punctual. Highly recommended!",
                        author = "Bob"
                    )
                )
            ),
            onBackClick = {},
            onBookingConfirmed = { bookingDetails ->
                println("Booking confirmed: $bookingDetails")
            }
        )
    }
}