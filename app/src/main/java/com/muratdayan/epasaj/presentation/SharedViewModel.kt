package com.muratdayan.epasaj.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratdayan.epasaj.core.common.Resource
import com.muratdayan.epasaj.domain.model.network_models.response_models.ProductModel
import com.muratdayan.epasaj.domain.usecase.network_usecases.GetUserDetailsByTokenUseCase
import com.muratdayan.epasaj.domain.usecase.network_usecases.GetUserInfoByIdUseCase
import com.muratdayan.epasaj.presentation.base.UserInfoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val getUserInfoByIdUseCase: GetUserInfoByIdUseCase,
    private val getUserDetailsByTokenUseCase: GetUserDetailsByTokenUseCase
) : ViewModel() {
    private val _product = MutableLiveData<ProductModel?>()
    val product: LiveData<ProductModel?> get() = _product

    fun setProduct(product: ProductModel?) {
        _product.value = product
    }

    private val _userInfoState = MutableStateFlow(UserInfoState())
    val userInfoState: StateFlow<UserInfoState>
        get() = _userInfoState

    // id'ye göre user bilgilerinin alınması
    /*fun getUserInfoById(userId: Int) {
        getUserInfoByIdUseCase(userId).onEach { resource->
            when(resource){
                is Resource.Error -> {
                    _userInfoState.value = UserInfoState(
                        error = resource.msg ?: "An unexpected error occurred"
                    )
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
*/
    // tokene göre user bilgilerinin alınması
    fun getUserDetailsByToken(token:String) {
        getUserDetailsByTokenUseCase(token).onEach { resource->
            when(resource){
                is Resource.Error -> {
                    _userInfoState.value = UserInfoState(
                        error = resource.msg ?: "An unexpected error occurred"
                    )
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