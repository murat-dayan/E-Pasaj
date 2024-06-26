package com.muratdayan.epasaj.data.remote.dto

data class ProductDto(
    val availabilityStatus: String,
    val brand: String?,
    val category: String,
    val description: String,
    val dimensions: DimensionsDto,
    val discountPercentage: Double,
    val id: Int,
    val images: List<String>,
    val meta: MetaDto,
    val minimumOrderQuantity: Int,
    val price: Double,
    val rating: Double,
    val returnPolicy: String,
    val reviews: List<ReviewDto>,
    val shippingInformation: String,
    val sku: String,
    val stock: Int,
    val tags: List<String>,
    val thumbnail: String,
    val title: String,
    val warrantyInformation: String,
    val weight: Int
)