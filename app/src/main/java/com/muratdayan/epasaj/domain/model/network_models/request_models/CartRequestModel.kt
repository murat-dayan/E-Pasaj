package com.muratdayan.epasaj.domain.model.network_models.request_models

data class CartRequestModel(
    val userId: Int,
    val products: List<ProductRequestModel>
)