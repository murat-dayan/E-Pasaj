package com.muratdayan.epasaj.presentation.orders

import com.muratdayan.epasaj.domain.model.network_models.response_models.CartModel

// Resource dönüşünün state temsili
data class OrdersState(
    val orders: List<CartModel>? = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = "",
)
