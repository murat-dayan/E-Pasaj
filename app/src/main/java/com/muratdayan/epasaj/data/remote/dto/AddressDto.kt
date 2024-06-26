package com.muratdayan.epasaj.data.remote.dto

data class AddressDto(
    val address: String,
    val city: String,
    val coordinates: CoordinatesDto,
    val country: String,
    val postalCode: String,
    val state: String,
    val stateCode: String
)