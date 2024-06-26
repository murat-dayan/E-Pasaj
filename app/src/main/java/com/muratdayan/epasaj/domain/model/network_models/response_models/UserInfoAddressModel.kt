package com.muratdayan.epasaj.domain.model.network_models.response_models

import java.io.Serializable


data class UserInfoAddressModel(
    val address: String?,
    val city: String?,
    val country: String?,
    val state: String?,
) :Serializable
