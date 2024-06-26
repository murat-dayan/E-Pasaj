package com.muratdayan.epasaj.domain.usecase.network_usecases

import com.muratdayan.epasaj.domain.repository.NetworkRepository
import javax.inject.Inject

class GetAllProductsUseCase @Inject constructor(
    private val networkRepository: NetworkRepository
) {
    operator fun invoke() = networkRepository.getAllProducts()
}