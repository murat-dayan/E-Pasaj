package com.muratdayan.epasaj.presentation.base

import com.muratdayan.epasaj.domain.model.network_models.response_models.UserInfoModel

// birçok yerde dinlenilen user bilgilerini dinleyen state
data class UserInfoState(
    val isLoading: Boolean = false,
    val userInfo: UserInfoModel? = null,
    val error: String? = ""
)
