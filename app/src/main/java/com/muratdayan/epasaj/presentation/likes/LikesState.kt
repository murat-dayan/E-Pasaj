package com.muratdayan.epasaj.presentation.likes

import com.muratdayan.epasaj.domain.model.database_models.UserLikesModel

// like state Resource sınıf temsili
data class LikesState(
    val likes: List<UserLikesModel>? = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = ""
)
