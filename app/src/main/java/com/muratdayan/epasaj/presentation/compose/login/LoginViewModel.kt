package com.muratdayan.epasaj.presentation.compose.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratdayan.epasaj.core.common.Resource
import com.muratdayan.epasaj.domain.model.network_models.request_models.RequestLoginUserModel
import com.muratdayan.epasaj.domain.usecase.network_usecases.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    // user state
    private val _userState = MutableStateFlow(UserState())
    val userState: StateFlow<UserState>
        get() = _userState

    // login fonksiyonundan dönüş datasına göre state güncelleme
    fun login(username: String, password: String) {
        val requestLoginUserModel = RequestLoginUserModel(username, password)

        loginUseCase(requestLoginUserModel).onEach { resource ->

            when (resource) {
                is Resource.Success -> {
                    _userState.value = UserState(userModel = resource.data)
                }

                is Resource.Error -> {
                    _userState.value = UserState(error = resource.msg)
                }

                is Resource.Loading -> {
                    _userState.value = UserState(isLoading = true)
                }

            }
        }.launchIn(viewModelScope)
    }


}