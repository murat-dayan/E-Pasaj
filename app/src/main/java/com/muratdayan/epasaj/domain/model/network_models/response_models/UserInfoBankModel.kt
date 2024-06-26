package com.muratdayan.epasaj.domain.model.network_models.response_models

import java.io.Serializable

data class UserInfoBankModel(
    val cardExpire: String?,
    val cardNumber: String?,
    val cardType: String?,
    val currency: String?,
    val iban: String?
): Serializable
