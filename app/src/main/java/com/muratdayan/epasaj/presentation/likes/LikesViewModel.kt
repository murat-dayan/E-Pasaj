package com.muratdayan.epasaj.presentation.likes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratdayan.epasaj.core.common.Resource
import com.muratdayan.epasaj.domain.model.database_models.UserLikesModel
import com.muratdayan.epasaj.domain.usecase.database_usecases.DeleteAllUserLikesUseCase
import com.muratdayan.epasaj.domain.usecase.database_usecases.GetUsersLikesByUserIdUseCase
import com.muratdayan.epasaj.domain.usecase.network_usecases.GetProductByIdUseCase
import com.muratdayan.epasaj.presentation.detail.DetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LikesViewModel @Inject constructor(
    private val getUsersLikesByUserIdUseCase: GetUsersLikesByUserIdUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val deleteAllUserLikesUseCase: DeleteAllUserLikesUseCase
): ViewModel(){



    private val _productDetailState = MutableStateFlow(DetailState())
    val productDetailState : StateFlow<DetailState>
        get() = _productDetailState

    private val _clearResult = MutableSharedFlow<Boolean>()
    val clearResult: SharedFlow<Boolean> = _clearResult.asSharedFlow()



    // likes tablosundan product id'ler alınır ve bu idlere göre productların verileri çekilir
    fun getUsersLikesByUserId(userId: Int){
        getUsersLikesByUserIdUseCase(userId).onEach { resource ->
            when(resource){
                is Resource.Error -> {
                    // not needed
                }
                is Resource.Loading -> {
                    // not needed
                }
                is Resource.Success -> {
                    resource.data?.forEach { userLikesModel->
                        getProductById(userLikesModel.productId)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    // id'lere göre product veri çekilmesi
    fun getProductById(id:Int){
        getProductByIdUseCase(id).onEach { resource ->
            when(resource){
                is Resource.Error -> {
                    _productDetailState.value = DetailState(error = resource.msg)
                }
                is Resource.Loading -> {
                    _productDetailState.value = DetailState(isLoading = true)
                }
                is Resource.Success -> {
                    _productDetailState.value = DetailState(product = resource.data)
                }
            }

        }.launchIn(viewModelScope)
    }

    fun deleteAllLikes(){
        viewModelScope.launch {
            val result=deleteAllUserLikesUseCase.invoke()
            _clearResult.emit(result)
        }
    }


}