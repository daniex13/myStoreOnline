package com.example.mystoreonline.home.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mystoreonline.home.data.network.response.Product
import com.example.mystoreonline.ui.theme.MyStoreOnlineTheme
import kotlin.math.roundToInt

@Composable
fun ProductDetail(product: Product?, onAddProduct: (Product?) -> Unit) {
    var rating by remember { mutableFloatStateOf(1f) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                .fillMaxWidth()
                .weight(0.7f)
                .align(Alignment.CenterHorizontally)
        ) {
            AsyncImage(
                model = product?.image,
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .border(1.dp, Color.Black)
                    .align(Alignment.Center),
                contentScale = ContentScale.Fit
            )
        }
        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .fillMaxSize()
                .weight(1f)
        ) {
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    product?.title ?: "",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold, modifier = Modifier.weight(1.5f)
                )
                Text(
                    product?.price?.toString() ?: "0",
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.3f),
                    textAlign = TextAlign.End
                )
            }
            Text(text = product?.description ?: "", modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f).padding(10.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    StarRatingBar(
                        maxStars = 5,
                        rating = product?.rating?.rate?.roundToInt() ?: 1,
                        onRatingChanged = {
                            rating = it
                        }
                    )
                    Text(
                        text = product?.rating?.rate?.toString() ?: "1",
                        modifier = Modifier.padding(start = 5.dp)
                    )
                    Text(text = "/" + product?.rating?.count.toString())
                }

                FloatingActionButton(
                    onClick = { onAddProduct(product) },
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
}

@Composable
fun StarRatingBar(
    maxStars: Int = 5,
    rating: Int,
    onRatingChanged: (Float) -> Unit
) {

    Row(
        modifier = Modifier.selectableGroup(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..maxStars) {
            val isSelected = i <= rating
            val icon = if (isSelected) Icons.Filled.Star else Icons.TwoTone.Star
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .selectable(
                        selected = isSelected,
                        onClick = {
                            onRatingChanged(i.toFloat())
                        }
                    )
            )

        }
    }
}

@Preview
@Composable
fun ProductDetailPreview() {
    MyStoreOnlineTheme {
//        ProductDetail()
    }
}