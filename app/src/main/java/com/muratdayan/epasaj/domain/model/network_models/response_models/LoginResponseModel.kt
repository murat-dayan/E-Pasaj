package com.muratdayan.epasaj.domain.model.network_models.response_models

data class LoginResponseModel(
    val token: String,
    val expiresIn: Int,
    val user: UserModel
)
