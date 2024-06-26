package com.muratdayan.epasaj.domain.usecase.network_usecases

import com.muratdayan.epasaj.domain.model.network_models.response_models.UserInfoModel
import com.muratdayan.epasaj.domain.repository.NetworkRepository
import javax.inject.Inject

class UpdateUserBodyUseCase @Inject constructor(
    private var networkRepository: NetworkRepository
){
    operator fun invoke(userId:Int, userInfoModel: UserInfoModel) = networkRepository.updateUserBody(userId, userInfoModel)

}