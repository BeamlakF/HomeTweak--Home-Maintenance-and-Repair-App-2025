@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.myapplication.ui.reusables

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment

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
