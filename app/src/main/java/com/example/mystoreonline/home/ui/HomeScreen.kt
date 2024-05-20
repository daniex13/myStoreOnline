package com.example.mystoreonline.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mystoreonline.home.data.network.response.Product
import com.example.mystoreonline.home.ui.component.OutStandingCard
import com.example.mystoreonline.home.ui.component.ProductDetail
import com.example.mystoreonline.home.ui.component.StandardCard
import com.example.mystoreonline.ui.theme.MyStoreOnlineTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(onNavigateToCart: () -> Unit) {
    val viewModel: HomeViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        viewModel.getProductList()
    }
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val showBottomSheet by viewModel.showBottomSheet.observeAsState(false)
    val textBadgeCart by viewModel.textBadgeCart.observeAsState(0)
    val isCategoryAllSelected by viewModel.isCategoryAllSelected.observeAsState(true)
    val isOtherCategorySelected by viewModel.isOtherCategorySelected.observeAsState("")
    val productDetail by viewModel.productDetail.observeAsState(null)
    val uiState = viewModel.uiState.collectAsState()

    HomeScreenStateless(
        onNavigateToCart,
        drawerState,
        scope,
        uiState,
        viewModel::getProductDetail,
        showBottomSheet,
        viewModel::onBottomSheetClose,
        productDetail,
        viewModel::getProductByCategory,
        isCategoryAllSelected,
        isOtherCategorySelected,
        textBadgeCart,
        viewModel::onAddOrUpdateProductToDataBase
    )
}

@Composable
fun HomeScreenStateless(
    onNavigateToCart: () -> Unit,
    drawerState: DrawerState,
    scope: CoroutineScope,
    uiState: State<UiState>,
    getProductDetail: (id: String) -> Unit,
    showBottomSheet: Boolean,
    onBottomSheetClose: () -> Unit,
    productDetail: Product?,
    onClickCategory: (category: String) -> Unit,
    isCategoryAllSelected: Boolean,
    isOtherCategorySelected: String,
    textBadgeCart: Int,
    onAddProduct: (Product?) -> Unit
) {
    when (val state = uiState.value) {

        is UiState.Loading -> {
            LoadingScreen()
        }

        is UiState.Error -> {
            Text(text = state.message)
        }

        is UiState.SuccessHome -> {
            NavigationDrawer(
                drawerState,
                onNavigateToCart,
                scope,
                state.listProducts,
                state.listCategory,
                getProductDetail,
                showBottomSheet,
                onBottomSheetClose,
                productDetail,
                onClickCategory,
                isCategoryAllSelected,
                isOtherCategorySelected,
                textBadgeCart,
                onAddProduct
            )
        }
    }

}

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun NavigationDrawer(
    drawerState: DrawerState,
    onNavigateToCart: () -> Unit,
    scope: CoroutineScope,
    listProducts: List<Product>,
    listCategory: List<String>,
    onClickProductDetail: (id: String) -> Unit,
    showBottomSheet: Boolean,
    onBottomSheetClose: () -> Unit,
    productDetail: Product?,
    onClickCategory: (category: String) -> Unit,
    isCategoryAllSelected: Boolean,
    isOtherCategorySelected: String,
    textBadgeCart: Int,
    onAddProduct: (Product?) -> Unit
) {
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
                    selected = isCategoryAllSelected,
                    onClick = {
                        onClickCategory("Todos")
                        scope.launch { drawerState.close() }
                    }
                )
                LazyColumn {
                    items(listCategory.size) {
                        NavigationDrawerItem(
                            label = { Text(listCategory[it]) },
                            selected = isOtherCategorySelected == listCategory[it],
                            onClick = {
                                onClickCategory(listCategory[it])
                                scope.launch { drawerState.close() }
                            }
                        )
                    }
                }

            }
        }
    ) {
        Scaffold(
            //Header
            topBar = {
                HomeHeader(onNavigateToCart, drawerState, scope, textBadgeCart)
            }) { scaffoldPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding)
            ) {
                HomeBody(
                    listProducts = listProducts,
                    productDetail = productDetail,
                    onClickProductDetail = onClickProductDetail,
                    showBottomSheet = showBottomSheet,
                    onBottomSheetClose = onBottomSheetClose,
                    onAddProduct = onAddProduct
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeHeader(
    onNavigateToCart: () -> Unit,
    drawerState: DrawerState,
    scope: CoroutineScope,
    textBadgeCart: Int
) {
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
            CartButton(onNavigateToCart, textBadgeCart)
        })

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CartButton(onNavigateToCart: () -> Unit, textBadgeCart: Int) {
    IconButton(
        modifier = Modifier
            .height(100.dp).width(100.dp),
        onClick = {
            onNavigateToCart()
        }) {
        if (textBadgeCart == 0) {
            Icon(
                Icons.Filled.ShoppingCart,
                contentDescription = "ShoppingCart"
            )
        } else if (textBadgeCart > 99) {
            BadgedBox(
                badge = {
                    Badge(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    ) { Text("+99") }
                }) {
                Icon(
                    Icons.Filled.ShoppingCart,
                    contentDescription = "ShoppingCart"
                )
            }
        } else {
            BadgedBox(
                badge = {
                    Badge(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    ) { Text(textBadgeCart.toString()) }
                }) {
                Icon(
                    Icons.Filled.ShoppingCart,
                    contentDescription = "ShoppingCart"
                )
            }
        }
    }
}

@Composable
fun HomeBody(
    listProducts: List<Product>,
    productDetail: Product?,
    onClickProductDetail: (id: String) -> Unit,
    showBottomSheet: Boolean,
    onBottomSheetClose: () -> Unit,
    onAddProduct: (Product?) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), modifier = Modifier.padding(16.dp)
        ) {
            item(
                span = {
                    GridItemSpan(maxCurrentLineSpan)
                }
            ) {
                OutStandingCard(listProducts[0], onAddProduct) {
                    onClickProductDetail(it.id.toString())
                }

            }
            items(listProducts.size - 1) {
                StandardCard(listProducts[it + 1], onAddProduct) { cardSelected ->
                    onClickProductDetail(cardSelected.id.toString())
                }
            }
        }
        BottomSheetDetail(productDetail, showBottomSheet, onBottomSheetClose, onAddProduct)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetDetail(
    productDetail: Product?,
    showBottomSheet: Boolean,
    onBottomSheetClose: () -> Unit,
    onAddProduct: (Product?) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                onBottomSheetClose()
            },
            sheetState = sheetState
        ) {
            // Sheet content
            ProductDetail(productDetail, onAddProduct)
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