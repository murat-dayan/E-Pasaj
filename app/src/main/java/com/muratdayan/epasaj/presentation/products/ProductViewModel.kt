package com.muratdayan.epasaj.presentation.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratdayan.epasaj.core.common.Resource
import com.muratdayan.epasaj.domain.usecase.network_usecases.GetAllProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase
) : ViewModel() {

    // state flow
    private val _productState = MutableStateFlow(ProductState())
    val productState : StateFlow<ProductState>
        get() = _productState

    init {
        getAllProducts()
    }


    // Resource classın dönüşüne göre state güncellenmesi
    private fun getAllProducts(){
        getAllProductsUseCase().onEach {resource ->
            when(resource){
                is Resource.Error -> {
                    _productState.value = ProductState(error = resource.msg)
                }
                is Resource.Loading -> {
                    _productState.value = ProductState(isLoading = true)
                }
                is Resource.Success -> {
                    _productState.value = ProductState(productList = resource.data)
                }
            }
        }.launchIn(viewModelScope)
    }


}