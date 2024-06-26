package com.muratdayan.epasaj.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratdayan.epasaj.core.common.Resource
import com.muratdayan.epasaj.domain.model.database_models.UserLikesModel
import com.muratdayan.epasaj.domain.model.network_models.request_models.CartRequestModel
import com.muratdayan.epasaj.domain.usecase.database_usecases.DeleteAllUserLikesUseCase
import com.muratdayan.epasaj.domain.usecase.database_usecases.DeleteLikeUseCase
import com.muratdayan.epasaj.domain.usecase.database_usecases.GetUsersLikesByUserIdUseCase
import com.muratdayan.epasaj.domain.usecase.database_usecases.InsertLikesUseCase
import com.muratdayan.epasaj.domain.usecase.network_usecases.AddToCartUseCase
import com.muratdayan.epasaj.domain.usecase.network_usecases.GetProductByIdUseCase
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
class DetailViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val insertLikesUseCase: InsertLikesUseCase,
    private val getUsersLikesByUserIdUseCase: GetUsersLikesByUserIdUseCase,
    private val deleteLikeUseCase: DeleteLikeUseCase,
    private val addToCartUseCase: AddToCartUseCase,
) : ViewModel() {
    // stateler
    private val _detailState = MutableStateFlow(DetailState())
    val detailState : StateFlow<DetailState>
        get() = _detailState

    private val _dbLikesState = MutableStateFlow(DbLikesState())
    val dbLikesState : StateFlow<DbLikesState>
        get() = _dbLikesState

    private val _addResult = MutableSharedFlow<String>()
    val addResult: SharedFlow<String> = _addResult.asSharedFlow()

    // user id'te göre kullanıcın db'deki like ürünlerin id'sini alır
    fun getUsersLikesByUserId(userId: Int){
        getUsersLikesByUserIdUseCase(userId).onEach { resource ->
            when(resource){
                is Resource.Error -> {
                    _dbLikesState.value = DbLikesState(error = resource.msg)
                }
                is Resource.Loading -> {
                    _dbLikesState.value = DbLikesState(isLoading = true)
                }
                is Resource.Success -> {
                    _dbLikesState.value = DbLikesState(likes = resource.data)
                }
            }
        }.launchIn(viewModelScope)
    }

    // ürün id'sine göre product verisini servisten alır
    fun getProductById(id:Int){
        getProductByIdUseCase(id).onEach { resource ->
            when(resource){
                is Resource.Error -> {
                    _detailState.value = DetailState(error = resource.msg)
                }
                is Resource.Loading -> {
                    _detailState.value = DetailState(isLoading = true)
                }
                is Resource.Success -> {
                    _detailState.value = DetailState(product = resource.data)
                }
            }

        }.launchIn(viewModelScope)
    }

    // db'ye like eklemesi yapar
    fun insertLikes(userLikesModel: UserLikesModel){
        viewModelScope.launch {
            insertLikesUseCase(userLikesModel)

        }
    }

    // dbye unlike silmesi yapar
    fun deleteLikes(userLikesModel: UserLikesModel){
        viewModelScope.launch {
            deleteLikeUseCase(userLikesModel)
        }
    }

    // servise add to cart fonksiyonunu yaptırtır cart request model body'si gönderir
    fun addToCart(cartRequestModel: CartRequestModel){
        viewModelScope.launch {
            addToCartUseCase(cartRequestModel).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _addResult.emit("Product Added")
                    }
                    is Resource.Error -> {
                        _addResult.emit(resource.msg ?: "Unknown error")
                    }
                    is Resource.Loading -> {
                        // Handle loading state if needed
                    }
                }
            }
        }
    }






}