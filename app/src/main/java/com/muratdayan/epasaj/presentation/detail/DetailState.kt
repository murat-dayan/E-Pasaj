package com.muratdayan.epasaj.presentation.detail

import com.muratdayan.epasaj.domain.model.network_models.response_models.ProductModel

// detail durum temsili
data class DetailState(
    val product: ProductModel? = null,
    val isLoading: Boolean = false,
    val error: String? = ""
)
