package com.example.mystoreonline.cart.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mystoreonline.cart.ui.component.CardCart
import com.example.mystoreonline.ui.theme.MyStoreOnlineTheme

@Composable
fun CartScreen(onNavigateToHomeScreen: () -> Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopCartScreen { onNavigateToHomeScreen() } },
        bottomBar = { BottomCartScreen() }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            LazyColumn {
                items(10) {
                    CardCart({}, {}, {})
                }
            }
        }
    }
}

@Composable
fun TopCartScreen(onNavigateToHomeScreen: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(top = 20.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier
            .padding(10.dp)
            .clickable {
                onNavigateToHomeScreen()
            }) {
            Icon(
                Icons.Filled.KeyboardArrowLeft,
                contentDescription = "ShoppingCart"
            )
        }
        Text(text = "Cart", modifier = Modifier.padding(10.dp))
    }
}

@Composable
fun BottomCartScreen() {
    val cardColors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.errorContainer,
    )
    Card(
        modifier = Modifier.fillMaxWidth(), colors = cardColors
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Total Amount: $2.500 CLP",
                modifier = Modifier.padding(10.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 25.dp, start = 20.dp, end = 20.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Purchase")
            }
        }
    }

}

@Preview
@Composable
fun RouteClosedFullScreenPreview() {
    MyStoreOnlineTheme {
        CartScreen {}
    }
}