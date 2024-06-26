package com.muratdayan.epasaj.domain.model.network_models.response_models

data class UserModel(
    val email: String,
    val firstName: String,
    val gender: String,
    val id: Int,
    val image: String,
    val lastName: String,
    val refreshToken: String,
    val token: String,
    val username: String
)
