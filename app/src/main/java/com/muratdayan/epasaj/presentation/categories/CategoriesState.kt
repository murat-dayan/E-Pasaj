package com.muratdayan.epasaj.presentation.categories

import com.muratdayan.epasaj.domain.model.network_models.response_models.CategoriesItemModel

// Resource sınıfını temsil eden Category state
data class CategoriesState(
    val categories: List<CategoriesItemModel>? = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
