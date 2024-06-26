package com.muratdayan.epasaj.presentation.detail

import com.muratdayan.epasaj.domain.model.database_models.UserLikesModel

data class DbLikesState(
    val likes: List<UserLikesModel>? = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = ""
)
