package com.muratdayan.epasaj.data.remote.repository

import com.muratdayan.epasaj.core.common.Resource
import com.muratdayan.epasaj.data.remote.mapper.toCartAddModel
import com.muratdayan.epasaj.data.remote.mapper.toCartModel
import com.muratdayan.epasaj.data.remote.mapper.toCategoriesItemModel
import com.muratdayan.epasaj.data.remote.mapper.toProductListModel
import com.muratdayan.epasaj.data.remote.mapper.toProductModel
import com.muratdayan.epasaj.data.remote.mapper.toUserInfoModel
import com.muratdayan.epasaj.data.remote.mapper.toUserModel
import com.muratdayan.epasaj.data.remote.service.NetworkService
import com.muratdayan.epasaj.domain.model.network_models.request_models.CartRequestModel
import com.muratdayan.epasaj.domain.model.network_models.request_models.RefreshTokenRequestModel
import com.muratdayan.epasaj.domain.model.network_models.request_models.RequestLoginUserModel
import com.muratdayan.epasaj.domain.model.network_models.response_models.CartAddModel
import com.muratdayan.epasaj.domain.model.network_models.response_models.CartModel
import com.muratdayan.epasaj.domain.model.network_models.response_models.CategoriesItemModel
import com.muratdayan.epasaj.domain.model.network_models.response_models.ProductListModel
import com.muratdayan.epasaj.domain.model.network_models.response_models.ProductModel
import com.muratdayan.epasaj.domain.model.network_models.response_models.RefreshTokenResponseModel
import com.muratdayan.epasaj.domain.model.network_models.response_models.UserInfoModel
import com.muratdayan.epasaj.domain.model.network_models.response_models.UserModel
import com.muratdayan.epasaj.domain.repository.NetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

// network servis işlemleri
class NetworkRepositoryImpl @Inject constructor(
    private val networkService: NetworkService
) : NetworkRepository {
    override fun getAllProducts(): Flow<Resource<ProductListModel>> = flow {
        emit(Resource.Loading())

        val productResponseDto = networkService.getAllProducts()

        val productList = productResponseDto.toProductListModel()

        emit(Resource.Success(productList))
    }.flowOn(Dispatchers.IO)
        .catch {
            emit(Resource.Error(it.message.toString()))
        }

    override fun searchProducts(searchText: String): Flow<Resource<ProductListModel>> = flow {
        emit(Resource.Loading())
        val productResponseDto = networkService.searchProducts(searchText)
        val productList = productResponseDto.toProductListModel()
        emit(Resource.Success(productList))
    }.flowOn(Dispatchers.IO)
        .catch {
            emit(Resource.Error(it.message.toString()))
        }

    override fun getAllCategories(): Flow<Resource<List<CategoriesItemModel>>> = flow {

        emit(Resource.Loading())

        val categoriesResponseDto = networkService.getAllCategories()

        val categoriesList = categoriesResponseDto.map { it.toCategoriesItemModel() }

        emit(Resource.Success(categoriesList))
    }.flowOn(Dispatchers.IO)
        .catch {
            emit(Resource.Error(it.message.toString()))
        }

    override fun getProductsByCategory(categoryName: String): Flow<Resource<ProductListModel>> = flow {
        emit(Resource.Loading())
        val productResponseDto = networkService.getProductsByCategory(categoryName)
        val productList = productResponseDto.toProductListModel()
        emit(Resource.Success(productList))
    }.flowOn(Dispatchers.IO)
        .catch {
            emit(Resource.Error(it.message.toString()))
        }

    override fun getProductById(productId: Int): Flow<Resource<ProductModel>> = flow {
        emit(Resource.Loading())
        val productResponseDto = networkService.getProductById(productId)
        val product = productResponseDto.toProductModel()
        emit(Resource.Success(product))
    }.flowOn(Dispatchers.IO)
        .catch {
            emit(Resource.Error(it.message.toString()))
        }

    override fun login(loginRequest: RequestLoginUserModel): Flow<Resource<UserModel>> = flow {
        emit(Resource.Loading())

        val userResponseDto = networkService.login(loginRequest)
        val user = userResponseDto.toUserModel()
        emit(Resource.Success(user))
    }.flowOn(Dispatchers.IO)
        .catch {
            emit(Resource.Error(it.message.toString()))
        }

    override fun getCartsByUserId(userId: Int): Flow<Resource<List<CartModel>>> = flow {
        emit(Resource.Loading())

        val cartsResponseDto = networkService.getCartsByUserId(userId)
        val carts = cartsResponseDto.carts.map { it.toCartModel() }
        emit(Resource.Success(carts))
    }

    override fun getUserInfoById(userId: Int): Flow<Resource<UserInfoModel>> = flow {
        emit(Resource.Loading())
        val userInfoResponseDto = networkService.getUserInfoById(userId)
        val userInfo = userInfoResponseDto.toUserInfoModel()
        emit(Resource.Success(userInfo))
    }.flowOn(Dispatchers.IO)
        .catch {
            emit(Resource.Error(it.message.toString()))
        }

    override fun updateUserBody(
        userId: Int,
        userInfoModel: UserInfoModel
    ): Flow<Resource<UserInfoModel>> = flow {
        emit(Resource.Loading())
        val userInfoResponseDto = networkService.updateUserBody(userId, userInfoModel)
        val userInfo = userInfoResponseDto.toUserInfoModel()
        emit(Resource.Success(userInfo))
    }.flowOn(Dispatchers.IO)
        .catch {
            emit(Resource.Error(it.message.toString()))
        }

    override fun addToCart(cartRequestModel: CartRequestModel): Flow<Resource<CartAddModel>> = flow {
        emit(Resource.Loading())
        try {
            val cartAddResponseDto = networkService.addToCart(cartRequestModel)
            val cartAddModel = cartAddResponseDto.toCartAddModel()
            println(cartAddModel.total)
            emit(Resource.Success(cartAddModel))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)

    override fun getUserDetailsByToken(token: String): Flow<Resource<UserInfoModel>> = flow {
        emit(Resource.Loading())
        val userInfoResponseDto = networkService.getUserDetailsByToken(token)
        val userInfo = userInfoResponseDto.toUserInfoModel()
        emit(Resource.Success(userInfo))
    }.flowOn(Dispatchers.IO)
        .catch {
            emit(Resource.Error(it.message.toString()))
        }

    override fun refreshToken(refreshTokenRequestModel: RefreshTokenRequestModel): Flow<Resource<RefreshTokenResponseModel>> = flow {
        emit(Resource.Loading())
        val response = networkService.refreshToken(refreshTokenRequestModel)
        emit(Resource.Success(response))

    }.flowOn(Dispatchers.IO)
        .catch {
            emit(Resource.Error(it.message.toString()))
        }


}