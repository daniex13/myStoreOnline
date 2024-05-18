package com.example.mystoreonline.navigation

sealed class Routes(val route: String){
    object HomeRoute:Routes("homeScreen")
    object CartRoute:Routes("cartScreen")
}
