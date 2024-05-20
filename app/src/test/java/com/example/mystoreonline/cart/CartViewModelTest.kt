package com.example.mystoreonline.cart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mystoreonline.TestDispatcherRule
import com.example.mystoreonline.cart.domain.DeleteProductCartUseCase
import com.example.mystoreonline.cart.domain.GetProductUseCase
import com.example.mystoreonline.cart.domain.UpdateProductCartUseCase
import com.example.mystoreonline.cart.ui.CartUiState
import com.example.mystoreonline.cart.ui.CartViewModel
import com.example.mystoreonline.cart.ui.model.CartModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CartViewModelTest {
    private val mockUpdateProductCartUseCase: UpdateProductCartUseCase = mockk()
    private val mockGetProductUseCase: GetProductUseCase = mockk()
    private val mockDeleteProductCartUseCase: DeleteProductCartUseCase = mockk()

    private lateinit var viewModel: CartViewModel

    @get:Rule
    var instantExecutorRule = TestDispatcherRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val mockedCartModel = CartModel(
        image = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
        title = "productTitle",
        price = 0.0,
        id = 0,
        quantity = 1,
    )

    @Before
    fun setUp() {
        viewModel = CartViewModel(
            mockUpdateProductCartUseCase,
            mockGetProductUseCase,
            mockDeleteProductCartUseCase,
        )
    }

    @Test
    fun `verify initial state`() {
        // Assert that the initial state is correct
        assertThat(viewModel.cartUiState.value, `is`(CartUiState.Loading))
    }

    @Test
    fun `verify get product cart list success`() {
        runTest {
            // Mock the getProductUseCase to return a list of cart models
            val mockCartList = listOf(mockedCartModel)

            coEvery { mockGetProductUseCase.invoke() } returns mockCartList
            // Call the getProductCartList() method
            viewModel.getProductCartList()

            // Assert that the cartUiState is updated with the list of cart models
            assertThat(viewModel.cartUiState.value, `is`(CartUiState.Success(mockCartList)))
        }
    }

    @Test
    fun `verify get product cart list error`() {
        runTest {
            // Mock the getProductUseCase to throw an exception
            val mockException = Exception("Error getting product cart list")

            coEvery { mockGetProductUseCase.invoke() } throws mockException


            // Call the getProductCartList() method
            viewModel.getProductCartList()

            // Assert that the cartUiState is updated with the error message
            assertThat(
                viewModel.cartUiState.value,
                `is`(CartUiState.Error(mockException.message.toString()))
            )
        }

    }

    @Test
    fun `verify get total price`() {
        runTest {
            // Mock the getProductUseCase to return a list of cart models
            val mockCartList = listOf(
                CartModel(
                    price = 10.0,
                    quantity = 2,
                    id = 1,
                    title = "Product 1",
                    image = "https://example.com/image1.jpg"
                ),
                CartModel(
                    price = 5.0,
                    quantity = 1,
                    id = 2,
                    title = "Product 2",
                    image = "https://example.com/image2.jpg"
                )
            )
            coEvery { mockGetProductUseCase.invoke() } returns mockCartList

            // Call the getTotalPrice() method
            viewModel.getTotalPrice()

            // Assert that the totalPrice LiveData is updated with the correct value
            assertThat(viewModel.totalPrice.value, `is`(25.0))
        }

    }

}