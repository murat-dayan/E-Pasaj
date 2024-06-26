package com.muratdayan.epasaj.domain.usecase.network_usecases

import com.muratdayan.epasaj.domain.model.network_models.request_models.RefreshTokenRequestModel
import com.muratdayan.epasaj.domain.repository.NetworkRepository
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(private val networkRepository: NetworkRepository) {
    operator fun invoke(refreshTokenRequestModel: RefreshTokenRequestModel) =
        networkRepository.refreshToken(refreshTokenRequestModel)
}