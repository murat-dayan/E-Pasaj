package com.muratdayan.epasaj.domain.model.network_models.request_models

data class RequestLoginUserModel(
    val username: String,
    val password: String,
    val expiresInMins:Int = 60,
)
