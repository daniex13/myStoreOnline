package com.example.mystoreonline.home.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import com.example.mystoreonline.home.data.network.response.Product
import com.example.mystoreonline.home.data.network.response.Rating
import com.example.mystoreonline.ui.theme.MyStoreOnlineTheme

@Composable
fun OutStandingCard(product: Product, onClickFavButton: (Product?) -> Unit, onClickCard: (product:Product) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(5.dp)
            .clickable{
                onClickCard(product)
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 20.dp, horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(Modifier.weight(1f)) {
                AsyncImage(
                    model = product.image,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                        .border(1.dp, Color.Black),
                    contentScale = ContentScale.Fit
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Destacado",
                    modifier = Modifier.padding(bottom = 10.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
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
            FloatingActionButton(
                onClick = { onClickFavButton(product) },
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .align(Alignment.Bottom),
                shape = CircleShape
            ) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        }

    }
}

@Composable
fun StandardCard(product: Product, onClickFavButton: (Product?) -> Unit, onClickCard: (product: Product) -> Unit) {
    Card(
        modifier = Modifier
            .height(200.dp)
            .padding(5.dp)
            .clickable {
                onClickCard(product)
            }
    ) {
        Column(
            Modifier
                .padding(12.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = product.image,
                contentDescription = null,
                modifier = Modifier
                    .height(100.dp)
                    .padding(horizontal = 20.dp, vertical = 5.dp)
                    .border(1.dp, Color.Black),
                contentScale = ContentScale.Fit
            )
            Text(
                text = product.title,
                fontSize = 12.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "$" + product.price.toString(), fontSize = 12.sp, maxLines = 1)
                FloatingActionButton(
                    onClick = { onClickFavButton(product) }, shape = CircleShape,
                    modifier = Modifier
                        .width(25.dp)
                        .height(25.dp)
                        .align(Alignment.Bottom),
                ) {
                    Icon(Icons.Filled.Add, "Floating action button.")
                }
            }
        }

    }
}

@Preview
@Composable
fun OutStandingCardGridPreview() {
    MyStoreOnlineTheme {
        OutStandingCard(
            Product(
                image = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
                title = "productTitle",
                price = 0.0,
                id = 0,
                category = "",
                description = "",
                rating = Rating(
                    rate = 0.0,
                    count = 0
                )
            ), {}, {}
        )
    }
}

@Preview
@Composable
fun StandardCardGridPreview() {
    MyStoreOnlineTheme {
        StandardCard(
            Product(
                image = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
                title = "productTitle",
                price = 0.0,
                id = 0,
                category = "",
                description = "",
                rating = Rating(
                    rate = 0.0,
                    count = 0
                )
            ), {}, {}
        )
    }
}