package com.muratdayan.epasaj.domain.model.network_models.response_models

import com.muratdayan.epasaj.data.remote.dto.AddressDto
import java.io.Serializable

data class UserInfoCompanyModel(
    val address: UserInfoAddressModel?,
    val department: String?,
    val name: String?,
    val title: String?
): Serializable
