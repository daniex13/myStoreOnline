package com.example.mystoreonline.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.mystoreonline.cart.ui.CartScreen
import com.example.mystoreonline.ui.theme.MyStoreOnlineTheme

@Composable
fun HomeScreen(onNavigateToCart: () -> Unit) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Cyan)){
        Text(text = "HomeScreen", modifier = Modifier.align(Alignment.Center).clickable {
            onNavigateToCart()
        })
    }
}

@Preview
@Composable
fun RouteClosedFullScreenPreview() {
    MyStoreOnlineTheme {
        HomeScreen {}
    }
}