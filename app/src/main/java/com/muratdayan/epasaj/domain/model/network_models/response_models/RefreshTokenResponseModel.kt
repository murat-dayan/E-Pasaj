package com.muratdayan.epasaj.domain.model.network_models.response_models

data class RefreshTokenResponseModel(
    val token: String,
    val refreshToken: String
)
