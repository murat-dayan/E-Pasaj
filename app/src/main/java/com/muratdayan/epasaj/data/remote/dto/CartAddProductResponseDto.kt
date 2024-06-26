package com.muratdayan.epasaj.data.remote.dto

data class CartAddProductResponseDto(
    val discountPercentage: Double,
    val discountedPrice: Double,
    val id: Int,
    val price: Double,
    val quantity: Int,
    val thumbnail: String,
    val title: String,
    val total: Double
)