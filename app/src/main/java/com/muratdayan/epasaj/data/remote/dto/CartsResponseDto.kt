package com.muratdayan.epasaj.data.remote.dto

data class CartsResponseDto(
    val carts: List<CartDto>,
    val limit: Int,
    val skip: Int,
    val total: Int
)