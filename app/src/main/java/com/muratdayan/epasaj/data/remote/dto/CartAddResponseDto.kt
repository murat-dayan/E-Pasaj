package com.muratdayan.epasaj.data.remote.dto

data class CartAddResponseDto(
    val discountedTotal: Double,
    val id: Int,
    val products: List<CartAddProductResponseDto>,
    val total: Double,
    val totalProducts: Int,
    val totalQuantity: Int,
    val userId: Int
)