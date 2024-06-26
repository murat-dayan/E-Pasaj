package com.muratdayan.epasaj.presentation.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratdayan.epasaj.core.common.Resource
import com.muratdayan.epasaj.domain.usecase.network_usecases.GetAllCategoriesUseCase
import com.muratdayan.epasaj.presentation.products.ProductState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase
) : ViewModel(){

    // state flow
    private val _categoryState = MutableStateFlow(CategoriesState())
    val categoryState : StateFlow<CategoriesState>
        get() = _categoryState

    init {
        getAllCategories()
    }

    // Resource dönüş datasına göre state'i günceller
    private fun getAllCategories(){
        getAllCategoriesUseCase().onEach { resources ->
            when(resources){
                is Resource.Loading -> {
                    _categoryState.value = CategoriesState(isLoading = true)
                }
                is Resource.Success -> {
                    _categoryState.value = CategoriesState(categories = resources.data)

                }
                is Resource.Error -> {
                    _categoryState.value = CategoriesState(error = resources.msg)
                }
            }

        }.launchIn(viewModelScope)
    }



}