package com.muratdayan.epasaj.domain.model.network_models.response_models

import java.io.Serializable

data class UserInfoModel(
    val age: Int?,
    val birthDate: String?,
    val bloodGroup: String?,
    val ein: String?,
    val email: String?,
    val eyeColor: String?,
    val firstName: String?,
    val gender: String?,
    val height: Double?,
    val id: Int?,
    val image: String?,
    val ip: String?,
    val lastName: String?,
    val macAddress: String?,
    val maidenName: String?,
    val password: String?,
    val phone: String?,
    val role: String?,
    val ssn: String?,
    val university: String?,
    val userAgent: String?,
    val username: String?,
    val weight: Double?,
    val address: UserInfoAddressModel?,
    val company: UserInfoCompanyModel?,
    val bank: UserInfoBankModel?
) : Serializable
