package com.example.mystoreonline

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mystoreonline.cart.ui.CartScreen
import com.example.mystoreonline.home.ui.HomeScreen
import com.example.mystoreonline.navigation.Routes
import com.example.mystoreonline.ui.theme.MyStoreOnlineTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyStoreOnlineTheme {
                val navigationController = rememberNavController()
                NavHost(navController = navigationController, startDestination = Routes.HomeRoute.route){
                    composable(Routes.HomeRoute.route) { HomeScreen(onNavigateToCart = { navigationController.navigate(Routes.CartRoute.route) }) }
                    composable(Routes.CartRoute.route) { CartScreen(onNavigateToHomeScreen = { navigationController.navigate(Routes.HomeRoute.route) }) }
                }

            }
        }
    }
}