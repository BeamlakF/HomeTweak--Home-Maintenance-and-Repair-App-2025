package com.example.myapplication.ui.customer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.example.myapplication.R
import com.example.myapplication.ui.theme.CardColor
import com.example.myapplication.ui.theme.DarkBlue
import com.example.myapplication.ui.theme.MutedGrey
import com.example.myapplication.ui.theme.OffWhite

@Composable
fun CategoryPage() {
    Column(
        modifier = Modifier
            .padding(vertical = 70.dp, horizontal = 20.dp)
            .background(color = OffWhite)
    ) {
        Text(
            text = "Categories",
            style = MaterialTheme.typography.displayLarge,
            color = DarkBlue
        )

        Spacer(modifier = Modifier.height(5.dp))

        Row {
            Text(
                text = "Choose Your Service",
                style = MaterialTheme.typography.bodyLarge,
                color = MutedGrey
            )
            Spacer(modifier = Modifier.width(100.dp))
            Text(
                text = ">>",
                color = DarkBlue
            )
        }

        Spacer(modifier = Modifier.height(45.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(16.dp)
        ) {
            CustomCard(img = R.drawable.plumber, name = "Plumber")

            Spacer(modifier = Modifier.width(25.dp))
            CustomCard(img = R.drawable.painter, name = "Painter")
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(16.dp)
        ) {
            CustomCard(img = R.drawable.electrician, name = "Electrician")
            Spacer(modifier = Modifier.width(25.dp))
            CustomCard(img = R.drawable.subcontractor, name = "Contractor")
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(16.dp)
        ) {
            CustomCard(img = R.drawable.gardner, name = "Gardner")
            Spacer(modifier = Modifier.width(25.dp))
            CustomCard(img = R.drawable.carpenter, name = "Carpenter")
        }
    }
}

@Composable
fun CustomCard(
    name: String,
    img: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(150.dp)
            .height(150.dp)
            .clickable { },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardColor
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = img),
                contentDescription = null,
                modifier = Modifier
                    .width(85.23.dp)
                    .height(85.23.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge,
                color = MutedGrey,
                textAlign = TextAlign.Center
            )
        }
    }
}
