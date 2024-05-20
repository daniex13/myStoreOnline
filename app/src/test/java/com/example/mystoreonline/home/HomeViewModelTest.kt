package com.example.mystoreonline.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mystoreonline.TestDispatcherRule
import com.example.mystoreonline.core.util.AddOrMinus
import com.example.mystoreonline.home.data.network.response.Product
import com.example.mystoreonline.home.data.network.response.Rating
import com.example.mystoreonline.home.domain.AddOrUpdateProductFromHomeUseCase
import com.example.mystoreonline.home.domain.GetAllProductsFromDataBase
import com.example.mystoreonline.home.domain.GetCategoriesListUseCase
import com.example.mystoreonline.home.domain.GetProductDetailUseCase
import com.example.mystoreonline.home.domain.GetProductListUseCase
import com.example.mystoreonline.home.domain.GetProductsByCategoriesUseCase
import com.example.mystoreonline.home.ui.HomeViewModel
import com.example.mystoreonline.home.ui.UiState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.log.Logger.Companion.invoke
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.mock

class HomeViewModelTest {

    private val mockGetProductListUseCase: GetProductListUseCase = mockk()
    private val mockGetProductDetailUseCase: GetProductDetailUseCase = mockk()
    private val mockGetProductsByCategoriesUseCase: GetProductsByCategoriesUseCase = mockk()
    private val mockGetCategoriesListUseCase: GetCategoriesListUseCase = mockk()
    private val mockAddOrUpdateProductFromHomeUseCase: AddOrUpdateProductFromHomeUseCase = mockk()
    private val mockGetAllProductsFromDataBase: GetAllProductsFromDataBase = mockk()

    private lateinit var viewModel: HomeViewModel

    @get:Rule
    var instantExecutorRule = TestDispatcherRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val mockedProduct = Product(
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
    )

    @Before
    fun setUp() {
        viewModel = HomeViewModel(
            mockGetProductListUseCase,
            mockGetProductDetailUseCase,
            mockGetProductsByCategoriesUseCase,
            mockGetCategoriesListUseCase,
            mockAddOrUpdateProductFromHomeUseCase,
            mockGetAllProductsFromDataBase
        )
    }

    @Test
    fun `verify initial state`() {
        // Assert that the initial state is correct
        assertThat(viewModel.uiState.value, `is`(UiState.Loading))
    }

    @Test
    fun `verify get product list success`() {
        runTest {
            // Mock the getProductListUseCase to return a list of products
            val mockProductList = listOf(mockedProduct)
            val mockCategoriesList = listOf("category1", "category2")


            // Call the getProductList() method
            coEvery { mockGetProductListUseCase.invoke() } returns mockProductList
            coEvery { mockGetCategoriesListUseCase.invoke() } returns mockCategoriesList
            coEvery { mockGetAllProductsFromDataBase.invoke() } returns 0

            viewModel.getProductList()

            // Assert that the uiState is updated with the list of products
            assertThat(
                viewModel.uiState.value,
                `is`(UiState.SuccessHome(mockProductList, mockCategoriesList))
            )
        }

    }

    @Test
    fun `verify get product list error`() {
        runTest {
            // Mock the getProductListUseCase to throw an exception
            val mockException = Exception("Error getting product list")


            // Call the getProductList() method
            coEvery { mockGetProductListUseCase.invoke() } throws mockException

            viewModel.getProductList()

            // Assert that the uiState is updated with the error message
            assertThat(
                viewModel.uiState.value,
                `is`(UiState.Error(mockException.message.toString()))
            )
        }

    }

    @Test
    fun `verify get product detail success`() {
        // Mock the getProductDetailUseCase to throw an exception
        val mockException = Exception("Error getting product detail")

        coEvery { mockGetProductDetailUseCase.invoke("1") } returns mockedProduct

        // Call the getProductDetail() method
        viewModel.getProductDetail("1")

        // Assert that the productDetail LiveData is updated with the product
        assertThat(viewModel.productDetail.value, `is`(mockedProduct))
    }

    @Test
    fun `verify get product detail error`() {
        // Mock the getProductDetailUseCase to throw an exception
        val mockException = Exception("Error getting product detail")

        coEvery { mockGetProductDetailUseCase.invoke("1") } throws mockException

        // Call the getProductDetail() method
        viewModel.getProductDetail("1")

        // Assert that the uiState is updated with the error message
        assertThat(viewModel.uiState.value, `is`(UiState.Error(mockException.message.toString())))
    }

    @Test
    fun `verify add or update product to cart error`() {
        runTest {

            coEvery { mockGetAllProductsFromDataBase.invoke() } returns 1
            coEvery { mockAddOrUpdateProductFromHomeUseCase.invoke(mockedProduct) }
            // Call the onAddOrUpdateProductToDataBase() method
            viewModel.onAddOrUpdateProductToDataBase(null)

            // Assert that the textBadgeCart LiveData is not updated
            assert(viewModel.textBadgeCart.value == null)
            assert(viewModel.uiState.value is UiState.Error)
        }
    }

}