package com.muratdayan.epasaj.presentation.compose.login

import com.muratdayan.epasaj.domain.model.network_models.response_models.UserModel

data class UserState(
    val userModel: UserModel?=null,
    val isLoading:Boolean=false,
    val error:String?=""
)
