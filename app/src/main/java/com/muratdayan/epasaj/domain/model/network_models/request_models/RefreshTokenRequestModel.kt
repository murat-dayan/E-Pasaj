package com.muratdayan.epasaj.domain.model.network_models.request_models

data class RefreshTokenRequestModel(
    val refreshToken: String,
    val expiresInMins: Int = 60
)
