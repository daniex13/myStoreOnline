package com.example.mystoreonline.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystoreonline.home.data.network.response.Product
import com.example.mystoreonline.home.domain.GetCategoriesListUseCase
import com.example.mystoreonline.home.domain.GetProductDetailUseCase
import com.example.mystoreonline.home.domain.GetProductListUseCase
import com.example.mystoreonline.home.domain.GetProductsByCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductListUseCase: GetProductListUseCase,
    private val getProductDetailUseCase: GetProductDetailUseCase,
    private val getProductsByCategoriesUseCase: GetProductsByCategoriesUseCase,
    private val getCategoriesListUseCase: GetCategoriesListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private val _showBottomSheet = MutableLiveData<Boolean>()
    val showBottomSheet: LiveData<Boolean> = _showBottomSheet

    private val _productDetail = MutableLiveData<Product?>()
    val productDetail: LiveData<Product?> = _productDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun onBottomSheetClose() {
        _showBottomSheet.value = false
    }

    fun getProductList() {
        viewModelScope.launch {
            try {
                val response = getProductListUseCase.invoke()
                getCategoriesList(response)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message.toString())
            }
        }
    }

    private fun getCategoriesList(listProducts: List<Product>) {
        viewModelScope.launch {
            try {
                val listCategory = getCategoriesListUseCase.invoke()
                _uiState.value = UiState.SuccessHome( listProducts = listProducts, listCategory = listCategory)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message.toString())
            }
        }
    }

    fun getProductDetail(id: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val detailProduct = getProductDetailUseCase.invoke(id)
                _showBottomSheet.value = true
                _productDetail.value = detailProduct
                _isLoading.value = false
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message.toString())
            }
        }
    }

    fun getProductByCategory(id: String) {
        viewModelScope.launch {
            try {
                val listProducts = if(id == "Todos"){
                    getProductListUseCase.invoke()
                }else{
                    getProductsByCategoriesUseCase.invoke(id)
                }

                val listCategory = getCategoriesListUseCase.invoke()
                _uiState.value = UiState.SuccessHome( listProducts = listProducts, listCategory = listCategory)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message.toString())
            }
        }
    }

}