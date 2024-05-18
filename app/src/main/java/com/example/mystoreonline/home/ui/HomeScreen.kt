package com.example.mystoreonline.home.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mystoreonline.ui.theme.MyStoreOnlineTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(onNavigateToCart: () -> Unit) {
    val viewModel: HomeViewModel = hiltViewModel()

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    HomeScreenStateless(onNavigateToCart, drawerState, scope)

}

@Composable
fun HomeScreenStateless(
    onNavigateToCart: () -> Unit,
    drawerState: DrawerState,
    scope: CoroutineScope,
) {

    //NavigationDrawer
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    "My Store Online",
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                    fontSize = 20.sp
                )
                Text("Categorias", modifier = Modifier.padding(start = 16.dp, bottom = 16.dp))
                NavigationDrawerItem(
                    label = { Text("Todos") },
                    selected = false,
                    onClick = { /*TODO*/ }
                )
                LazyColumn {
                    items(4) {
                        NavigationDrawerItem(
                            label = { Text("Categorias $it") },
                            selected = false,
                            onClick = { /*TODO*/ }
                        )
                    }
                }

            }
        }
    ) {
        Scaffold(
            //Header
            topBar = {
                HomeHeader(onNavigateToCart, drawerState, scope)
            }) { scaffoldPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding)
            ) {
                //Content
                HomeBody(scope)
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeHeader(onNavigateToCart: () -> Unit, drawerState: DrawerState, scope: CoroutineScope) {
    TopAppBar(
        title = {
            Text(text = "My Store Online")
        },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    drawerState.apply {
                        if (isClosed) open() else close()
                    }
                }
            }) {
                Icon(
                    Icons.Filled.Menu,
                    contentDescription = "ShoppingCart"
                )
            }
        },
        actions = {
            IconButton(onClick = {
                onNavigateToCart()
            }) {
                Icon(
                    Icons.Filled.ShoppingCart,
                    contentDescription = "ShoppingCart"
                )
            }
        })

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeBody(scope: CoroutineScope) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    Column {
        val list = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
        Text(text = list[0], modifier = Modifier.fillMaxWidth().clickable {
            showBottomSheet = true
        })
        LazyVerticalGrid(
            columns = GridCells.Fixed(2)
        ) {
            items(list.size - 1) {
                Text(text = list[it+1], modifier = Modifier.clickable {
                    showBottomSheet = true
                })

            }
        }
    }
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState
        ) {
            // Sheet content
            Button(onClick = {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        showBottomSheet = false
                    }
                }
            }) {
                Text("Hide bottom sheet")
            }
        }
    }


}

@Preview
@Composable
fun RouteClosedFullScreenPreview() {
    MyStoreOnlineTheme {
        HomeScreen {}
    }
}