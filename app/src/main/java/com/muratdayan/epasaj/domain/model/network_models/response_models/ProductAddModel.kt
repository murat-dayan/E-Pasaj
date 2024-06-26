package com.muratdayan.epasaj.domain.model.network_models.response_models

data class ProductAddModel(
    val discountPercentage: Double,
    val discountedPrice: Double,
    val id: Int,
    val price: Double,
    val quantity: Int,
    val thumbnail: String,
    val title: String,
    val total: Double
)
