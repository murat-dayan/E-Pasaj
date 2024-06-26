package com.muratdayan.epasaj.domain.model.network_models.response_models

data class CartProductModel(
    val discountPercentage: Double,
    val discountedTotal: Double,
    val id: Int,
    val price: Double,
    val quantity: Int,
    val thumbnail: String,
    val title: String,
    val total: Double
)
