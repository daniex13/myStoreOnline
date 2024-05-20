package com.example.mystoreonline.cart.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mystoreonline.cart.ui.model.CartModel
import com.example.mystoreonline.ui.theme.MyStoreOnlineTheme

@Composable
fun CardCart(
    product: CartModel,
    onClickDelete: (CartModel) -> Unit,
    onClickCountPlus: (CartModel) -> Unit,
    onClickCountMinus: (CartModel) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 20.dp, horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(Modifier.weight(1f).align(Alignment.CenterVertically)) {
                AsyncImage(
                    model = product.image,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                        .border(1.dp, Color.Black).fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Column(modifier = Modifier.weight(1f)){
                    Text(
                        text = product.title,
                        modifier = Modifier.padding(bottom = 10.dp),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 12.sp,
                    )
                    Text(
                        text = "$" + product.price.toString(),
                        modifier = Modifier.padding(bottom = 10.dp),
                        fontSize = 12.sp,
                    )
                }

                Button(
                    onClick = { onClickDelete(product) }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 30.dp).weight(1f)
                ) {
                    Text(text = "Delete", modifier = Modifier.padding(end = 10.dp))
                    Icon(Icons.Filled.Delete, "Floating action button.")
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxSize().weight(0.7f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FloatingActionButton(
                        onClick = { onClickCountMinus(product) }, shape = CircleShape,
                        modifier = Modifier
                            .width(25.dp)
                            .height(25.dp),
                    ) {
                        Text(
                            text = "-",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.CenterVertically),
                        )
                    }
                    Text(
                        text = product.quantity.toString(),
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        fontSize = 15.sp,
                    )
                    FloatingActionButton(
                        onClick = { onClickCountPlus(product) }, shape = CircleShape,
                        modifier = Modifier
                            .width(25.dp)
                            .height(25.dp),
                    ) {
                        Icon(
                            Icons.Filled.Add,
                            "Floating action button.",
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CardCartPreview() {
    MyStoreOnlineTheme {
        CardCart(
            CartModel(
                image = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
                title = "productTitle",
                price = 0.0,
                id = 0,
            ),
            onClickDelete = {},
            onClickCountPlus = {},
            onClickCountMinus = {}
        )
    }
}