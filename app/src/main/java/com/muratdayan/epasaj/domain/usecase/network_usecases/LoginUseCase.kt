package com.muratdayan.epasaj.domain.usecase.network_usecases

import com.muratdayan.epasaj.domain.model.network_models.request_models.RequestLoginUserModel
import com.muratdayan.epasaj.domain.repository.NetworkRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val networkRepository: NetworkRepository
) {
    operator fun invoke(loginRequest: RequestLoginUserModel) = networkRepository.login(loginRequest)
}