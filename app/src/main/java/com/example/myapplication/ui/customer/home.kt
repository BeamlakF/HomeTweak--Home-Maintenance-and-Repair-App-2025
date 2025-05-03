package com.example.myapplication.ui.customer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.ui.theme.CardColor
import com.example.myapplication.ui.theme.DarkBlue
import com.example.myapplication.ui.theme.MutedGrey
import com.example.myapplication.ui.theme.OffWhite

data class Category(val name: String, val img: Int)

@Composable
fun HomePage() {
    val browse = remember { mutableStateOf("") }

    val categoryList = listOf(
        Category("Plumber", R.drawable.plumber),
        Category("Painter", R.drawable.painter),
        Category("Carpenter", R.drawable.carpenter),
        Category("Electrician", R.drawable.electrician),
        Category("Contractor", R.drawable.subcontractor),
        Category("Gardner", R.drawable.gardner)
    )

    Column(
        modifier = Modifier
            .padding(vertical = 70.dp, horizontal = 20.dp)
            .background(color = OffWhite)
    ) {
        // Search Input Placeholder (e.g., TextField can be added here)

        Text(
            text = "Categories",
            color = DarkBlue,
            style = MaterialTheme.typography.displayLarge
        )

        Spacer(modifier = Modifier.height(20.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(15.dp)) {
            items(categoryList) { category ->
                CustomLazyCard(category = category, onClick = {
                    // Handle item click
                })
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Top Rated",
            style = MaterialTheme.typography.displayLarge,
            color = DarkBlue
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            modifier = Modifier.padding(50.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            items(5) { // Replace with real data
                LazyColumnCard()
            }
        }
    }
}

@Composable
fun CustomLazyCard(category: Category, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(150.dp)
            .clip(RoundedCornerShape(35.dp))
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = CardColor)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = category.img),
                contentDescription = null,
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = category.name,
                color = MutedGrey,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun LazyColumnCard() {
    Card(
        modifier = Modifier
            .width(320.dp)
            .height(100.dp)
            .clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(CardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.painter),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(50.dp))
            )
            Column {
                Text(
                    text = "Dawit Teshome",
                    style = MaterialTheme.typography.titleLarge,
                    color = DarkBlue
                )
                Text(
                    text = "Plumber",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MutedGrey
                )
                Text(
                    text = "Rating: 4.5/5",
                    color = DarkBlue
                )
            }
        }
    }
}
