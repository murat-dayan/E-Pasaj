package com.muratdayan.epasaj.data.remote.dto

data class CartDto(
    val discountedTotal: Double,
    val id: Int,
    val products: List<CartProductDto>,
    val total: Double,
    val totalProducts: Int,
    val totalQuantity: Int,
    val userId: Int
)