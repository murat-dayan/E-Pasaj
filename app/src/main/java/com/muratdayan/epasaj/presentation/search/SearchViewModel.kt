package com.muratdayan.epasaj.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratdayan.epasaj.core.common.Resource
import com.muratdayan.epasaj.domain.usecase.network_usecases.SearchProductsUseCase
import com.muratdayan.epasaj.presentation.products.ProductState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchProductsUseCase: SearchProductsUseCase
): ViewModel() {

    private val _searchProductState = MutableStateFlow(ProductState())
    val searchProductState : StateFlow<ProductState>
        get() = _searchProductState


    // gelen string değerine göre servisten arama işlemini yaptırtır
    fun searchProducts(searchText:String){
        searchProductsUseCase(searchText).onEach { resource ->

            when(resource){
                is Resource.Error -> {
                    _searchProductState.value = ProductState(error = resource.msg)
                }
                is Resource.Loading -> {
                    _searchProductState.value = ProductState(isLoading = true)
                }
                is Resource.Success -> {
                    _searchProductState.value = ProductState(productList = resource.data)
                }
            }

        }.launchIn(viewModelScope)
    }



}