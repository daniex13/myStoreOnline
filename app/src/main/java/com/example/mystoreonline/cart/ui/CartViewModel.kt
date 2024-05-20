package com.example.mystoreonline.cart.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystoreonline.cart.domain.DeleteProductCartUseCase
import com.example.mystoreonline.cart.domain.UpdateProductCartUseCase
import com.example.mystoreonline.cart.domain.GetProductUseCase
import com.example.mystoreonline.cart.ui.model.CartModel
import com.example.mystoreonline.core.util.AddOrMinus
import com.example.mystoreonline.home.data.network.response.Product
import com.example.mystoreonline.home.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val updateProductCartUseCase: UpdateProductCartUseCase,
    private val getProductUseCase: GetProductUseCase,
    private val deleteProductCartUseCase: DeleteProductCartUseCase
) : ViewModel() {

    private val _cartUiState = MutableStateFlow<CartUiState>(CartUiState.Loading)
    val cartUiState: StateFlow<CartUiState> = _cartUiState

    private val _totalPrice = MutableLiveData<Double>()
    val totalPrice: LiveData<Double> = _totalPrice

    fun onUpdateProductToDataBase(cartModel: CartModel) {
        viewModelScope.launch {

            updateProductCartUseCase(cartModel, AddOrMinus.ADD)

/*            _textQuantity.value = getProductUseCase.invoke().find {
                it.id == cartModel.id
            }?.quantity*/
            getProductCartList()

        }
    }

    fun onMinusProductToDataBase(cartModel: CartModel) {
        viewModelScope.launch {
            updateProductCartUseCase(cartModel, AddOrMinus.MINUS)
            getProductCartList()
        }
    }

    fun onItemRemove(cartModel: CartModel) {
        viewModelScope.launch {
            deleteProductCartUseCase(cartModel)
            getProductCartList()
        }
    }

    fun getProductCartList(){
        viewModelScope.launch {
            _cartUiState.value = CartUiState.Success(getProductUseCase.invoke())
            getTotalPrice()
        }
    }

    private fun getTotalPrice(){
        viewModelScope.launch {
            _totalPrice.value = getProductUseCase.invoke().sumOf {
                it.price * it.quantity
            }
        }
    }

}