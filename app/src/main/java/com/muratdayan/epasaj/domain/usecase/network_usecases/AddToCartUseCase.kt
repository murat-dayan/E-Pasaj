package com.muratdayan.epasaj.domain.usecase.network_usecases

import com.muratdayan.epasaj.domain.model.network_models.request_models.CartRequestModel
import com.muratdayan.epasaj.domain.repository.NetworkRepository
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val networkRepository: NetworkRepository
){
    operator fun invoke(cartRequestModel: CartRequestModel) = networkRepository.addToCart(cartRequestModel)
}