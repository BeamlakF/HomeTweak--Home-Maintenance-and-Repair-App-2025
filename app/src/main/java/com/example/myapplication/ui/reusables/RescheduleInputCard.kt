package com.example.myapplication.ui.reusables


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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
