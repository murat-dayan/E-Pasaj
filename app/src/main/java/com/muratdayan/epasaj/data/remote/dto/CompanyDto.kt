package com.muratdayan.epasaj.data.remote.dto

data class CompanyDto(
    val address: AddressDto,
    val department: String,
    val name: String,
    val title: String
)