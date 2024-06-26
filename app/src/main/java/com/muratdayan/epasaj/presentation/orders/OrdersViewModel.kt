package com.muratdayan.epasaj.presentation.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratdayan.epasaj.core.common.Resource
import com.muratdayan.epasaj.domain.usecase.network_usecases.GetCartsByUserIdUseCase
import com.muratdayan.epasaj.presentation.products.ProductState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val getCartsByUserIdUseCase: GetCartsByUserIdUseCase
): ViewModel(){

    // order state flow
    private val _ordersState = MutableStateFlow(OrdersState())
    val ordersState : StateFlow<OrdersState>
        get() = _ordersState



    // user id'ye göre kullanıcı siparişlerinin alınması
    fun getCartsByUserId(userId:Int){
        getCartsByUserIdUseCase(userId).onEach { resource->
            when(resource){
                is Resource.Error -> {
                    _ordersState.value = OrdersState(error = resource.msg)
                }
                is Resource.Loading ->{
                    _ordersState.value = OrdersState(isLoading = true)
                }
                is Resource.Success -> {
                    _ordersState.value = OrdersState(orders = resource.data)
                }
            }
        }.launchIn(viewModelScope)
    }







}