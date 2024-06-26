package com.muratdayan.epasaj.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratdayan.epasaj.core.common.Resource
import com.muratdayan.epasaj.domain.model.network_models.response_models.UserInfoModel
import com.muratdayan.epasaj.domain.usecase.network_usecases.UpdateUserBodyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val updateUserBodyUseCase: UpdateUserBodyUseCase
) : ViewModel() {

    private val _userInfoState = MutableStateFlow(UserInfoState())
    val userInfoState: StateFlow<UserInfoState>
        get() = _userInfoState


    // update user info
    fun updateUserBody(userId: Int, userInfoModel: UserInfoModel) {
        updateUserBodyUseCase(userId,userInfoModel).onEach { resource->
            when(resource){
                is Resource.Error -> {
                    _userInfoState.value = UserInfoState(error = resource.msg)
                }
                is Resource.Loading -> {
                    _userInfoState.value = UserInfoState(isLoading = true)
                }
                is Resource.Success -> {
                    _userInfoState.value = UserInfoState(userInfo = resource.data)
                }
            }
        }.launchIn(viewModelScope)
    }
}