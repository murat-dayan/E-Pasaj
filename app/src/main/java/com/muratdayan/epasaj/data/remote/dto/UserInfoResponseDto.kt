package com.muratdayan.epasaj.data.remote.dto

data class UserInfoResponseDto(
    val address: AddressDto,
    val age: Int,
    val bank: BankDto,
    val birthDate: String,
    val bloodGroup: String,
    val company: CompanyDto,
    val crypto: CryptoDto,
    val ein: String,
    val email: String,
    val eyeColor: String,
    val firstName: String,
    val gender: String,
    val hair: HairDto,
    val height: Double,
    val id: Int,
    val image: String,
    val ip: String,
    val lastName: String,
    val macAddress: String,
    val maidenName: String,
    val password: String,
    val phone: String,
    val role: String,
    val ssn: String,
    val university: String,
    val userAgent: String,
    val username: String,
    val weight: Double
)