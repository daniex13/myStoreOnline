package com.example.mystoreonline.cart.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mystoreonline.cart.ui.component.CardCart
import com.example.mystoreonline.cart.ui.model.CartModel
import com.example.mystoreonline.home.ui.LoadingScreen
import com.example.mystoreonline.ui.theme.MyStoreOnlineTheme

@Composable
fun CartScreen(onNavigateToHomeScreen: () -> Unit) {
    val cartViewModel: CartViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        cartViewModel.getProductCartList()
    }
    val cartUiState = cartViewModel.cartUiState.collectAsState()
    val totalPrice by cartViewModel.totalPrice.observeAsState(0.0)

    CartScreenStateless(
        onNavigateToHomeScreen,
        cartUiState,
        cartViewModel::onItemRemove,
        cartViewModel::onMinusProductToDataBase,
        cartViewModel::onUpdateProductToDataBase,
        totalPrice
    )


}

@Composable
fun CartScreenStateless(
    onNavigateToHomeScreen: () -> Unit,
    cartUiState: State<CartUiState>,
    onItemRemove: (CartModel) -> Unit,
    onMinusProductToDataBase: (CartModel) -> Unit,
    onUpdateProductToDataBase: (CartModel) -> Unit,
    totalPrice: Double,
) {
    when (val state = cartUiState.value) {

        is CartUiState.Loading -> {
            LoadingScreen()
        }

        is CartUiState.Error -> {
            Text(text = state.message)
        }

        is CartUiState.Success -> {
            CartScreenScaffold(
                onNavigateToHomeScreen,
                state.cartList,
                onItemRemove,
                onMinusProductToDataBase,
                onUpdateProductToDataBase,
                totalPrice
            )
        }
    }

}

@Composable
fun CartScreenScaffold(
    onNavigateToHomeScreen: () -> Unit,
    cartList: List<CartModel>,
    onItemRemove: (CartModel) -> Unit,
    onMinusProductToDataBase: (CartModel) -> Unit,
    onUpdateProductToDataBase: (CartModel) -> Unit,
    totalPrice: Double,
) {
    if (cartList.isEmpty()) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { TopCartScreen { onNavigateToHomeScreen() } })
        {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Filled.ShoppingCart,
                    modifier = Modifier
                        .padding(10.dp)
                        .width(100.dp)
                        .height(100.dp),
                    contentDescription = "ShoppingCart"
                )
                Text(text = "No products in cart")
            }
        }

    } else {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { TopCartScreen { onNavigateToHomeScreen() } },
            bottomBar = { BottomCartScreen(totalPrice) }) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                LazyColumn {
                    items(cartList.size) { product ->
                        CardCart(
                            cartList[product],
                            onItemRemove,
                            onUpdateProductToDataBase,
                            onMinusProductToDataBase,
                        )
                    }
                }

            }
        }
    }

}

@Composable
fun TopCartScreen(onNavigateToHomeScreen: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp), verticalAlignment = Alignment.CenterVertically
    ) {
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
fun BottomCartScreen(totalPrice: Double) {
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
                text = "Total Amount: $${totalPrice} CLP",
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