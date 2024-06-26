package com.muratdayan.epasaj.domain.model.network_models.response_models

data class CartModel(
    val discountedTotal: Double,
    val id: Int,
    val products: List<CartProductModel>,
    val total: Double,
    val totalProducts: Int,
    val totalQuantity: Int,
    val userId: Int
)
