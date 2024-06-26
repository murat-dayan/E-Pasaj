package com.muratdayan.epasaj.presentation.category_products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratdayan.epasaj.core.common.Resource
import com.muratdayan.epasaj.domain.usecase.network_usecases.GetProductsByCategoryUseCase
import com.muratdayan.epasaj.presentation.products.ProductState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CategoryProductsViewModel @Inject constructor(
    private val getProductsByCategoryUseCase: GetProductsByCategoryUseCase
) : ViewModel(){

    // Product state'i temsil eden stateflow
    private val _categoryProductState = MutableStateFlow(ProductState())
    val categoryProductState : StateFlow<ProductState>
        get() = _categoryProductState


    // Resource dönüş datasına göre state'i günceller
    fun getProductsByCategory(categoryName:String){
        getProductsByCategoryUseCase(categoryName).onEach {resource ->
            when(resource){
                is Resource.Error -> {
                    _categoryProductState.value = ProductState(error = resource.msg)
                }
                is Resource.Loading -> {
                    _categoryProductState.value = ProductState(isLoading = true)
                }
                is Resource.Success -> {
                    _categoryProductState.value = ProductState(productList = resource.data)
                }
            }

        }.launchIn(viewModelScope)
    }


}