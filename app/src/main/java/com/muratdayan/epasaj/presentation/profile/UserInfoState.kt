package com.muratdayan.epasaj.presentation.profile

import com.muratdayan.epasaj.domain.model.network_models.response_models.UserInfoModel

data class UserInfoState(
    val userInfo: UserInfoModel? = null,
    val error: String? = "",
    val isLoading: Boolean = false
)
