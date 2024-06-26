package com.muratdayan.epasaj.domain.repository

import com.muratdayan.epasaj.core.common.Resource
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
import kotlinx.coroutines.flow.Flow

interface NetworkRepository {

    fun getAllProducts() : Flow<Resource<ProductListModel>>

    fun searchProducts(searchText:String) : Flow<Resource<ProductListModel>>

    fun getAllCategories() : Flow<Resource<List<CategoriesItemModel>>>

    fun getProductsByCategory(categoryName:String) : Flow<Resource<ProductListModel>>

    fun getProductById(productId:Int) : Flow<Resource<ProductModel>>

    fun login(loginRequest :RequestLoginUserModel) : Flow<Resource<UserModel>>

    fun getCartsByUserId(userId:Int) : Flow<Resource<List<CartModel>>>

    fun getUserInfoById(userId:Int) : Flow<Resource<UserInfoModel>>

    fun updateUserBody(userId:Int, userInfoModel:UserInfoModel) : Flow<Resource<UserInfoModel>>

    fun addToCart(cartRequestModel: CartRequestModel) : Flow<Resource<CartAddModel>>

    fun getUserDetailsByToken(token: String) : Flow<Resource<UserInfoModel>>

    fun refreshToken(refreshTokenRequestModel: RefreshTokenRequestModel) : Flow<Resource<RefreshTokenResponseModel>>


}