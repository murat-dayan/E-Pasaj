package com.muratdayan.epasaj.domain.usecase.network_usecases

import com.muratdayan.epasaj.domain.repository.NetworkRepository
import javax.inject.Inject

class GetUserDetailsByTokenUseCase @Inject constructor(
    private val networkRepository: NetworkRepository
){

    operator fun invoke(token: String) = networkRepository.getUserDetailsByToken(token)
}