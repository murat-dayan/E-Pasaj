package com.muratdayan.epasaj.domain.model.network_models.response_models

data class ReviewModel(
    val comment: String,
    val date: String,
    val rating: Int,
    val reviewerEmail: String,
    val reviewerName: String
)
