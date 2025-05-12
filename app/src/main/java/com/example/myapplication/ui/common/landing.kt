package com.example.myapplication.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.ui.theme.DarkBlue
import com.example.myapplication.ui.theme.MutedGrey
import com.example.myapplication.ui.theme.OffWhite
import com.example.myapplication.ui.theme.SafetyOrange
import androidx.navigation.NavHostController
import com.example.myapplication.ui.navigation.Routes


@Composable
fun LandingPage(navController: NavHostController) {
    // UI for your landing screen
    Column(
        modifier = Modifier
            .padding(vertical = 50.dp)
            .background(color = OffWhite),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(50.dp))

//        Text(
//            text = "Find Trusted \n Home Service Provider",
//            textAlign = TextAlign.Center,
//            style = MaterialTheme.typography.headlineLarge,
//            color = DarkBlue,
//        )

        Text(
            text = "HomeTweak",
            style = MaterialTheme.typography.displayLarge,
            color = DarkBlue,
        )

        Text(
            text = "One Tap To Better Home",
            color = MutedGrey,
            style = MaterialTheme.typography.titleLarge
        )

//        Image(
//            painter = painterResource(id = R.drawable.plumber_flat_composition),
//            contentDescription = null,
//            modifier = Modifier
//                .fillMaxWidth()
//                .size(300.dp)
//        )

        Spacer(modifier = Modifier.height(10.dp))

        Image(
            painter = painterResource(id = R.drawable.home_tweak),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(100.dp))
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                navController.navigate(Routes.LOGIN)
            },
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier.width(200.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = SafetyOrange,
                contentColor = Color.White
            ),
        ) {
            Text(
                text = "Get Started",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

