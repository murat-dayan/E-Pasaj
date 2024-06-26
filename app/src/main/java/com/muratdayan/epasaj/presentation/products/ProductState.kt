package com.muratdayan.epasaj.presentation.products

import com.muratdayan.epasaj.domain.model.network_models.response_models.ProductListModel

// Resource dönüşünün state temsili
data class ProductState(
    val productList: ProductListModel?=null,
    val isLoading: Boolean = false,
    val error: String? = ""
)
