package com.muratdayan.epasaj.data.remote.dto

data class ProductResponseDto(
    val limit: Int,
    val products: List<ProductDto>,
    val skip: Int,
    val total: Int
)