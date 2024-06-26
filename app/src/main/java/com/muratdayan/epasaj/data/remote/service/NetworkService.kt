package com.muratdayan.epasaj.data.remote.service

import com.muratdayan.epasaj.data.remote.dto.CartAddResponseDto
import com.muratdayan.epasaj.data.remote.dto.CartsResponseDto
import com.muratdayan.epasaj.data.remote.dto.CategoriesItemDto
import com.muratdayan.epasaj.data.remote.dto.ProductDto
import com.muratdayan.epasaj.data.remote.dto.ProductResponseDto
import com.muratdayan.epasaj.data.remote.dto.UserInfoResponseDto
import com.muratdayan.epasaj.data.remote.dto.UserResponseDto
import com.muratdayan.epasaj.domain.model.network_models.request_models.CartRequestModel
import com.muratdayan.epasaj.domain.model.network_models.request_models.RefreshTokenRequestModel
import com.muratdayan.epasaj.domain.model.network_models.request_models.RequestLoginUserModel
import com.muratdayan.epasaj.domain.model.network_models.response_models.RefreshTokenResponseModel
import com.muratdayan.epasaj.domain.model.network_models.response_models.UserInfoModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {

    @GET("products")
    suspend fun getAllProducts(): ProductResponseDto

    @GET("products/search")
    suspend fun searchProducts(@Query("q") searchText:String): ProductResponseDto

    @GET("products/categories")
    suspend fun getAllCategories() : List<CategoriesItemDto>

    @GET("products/category/{categoryName}")
    suspend fun getProductsByCategory(@Path("categoryName") categoryName:String): ProductResponseDto

    @GET("products/{productId}")
    suspend fun getProductById(@Path("productId") productId:Int): ProductDto


    @POST("auth/login")
    suspend fun login(@Body loginRequest: RequestLoginUserModel) : UserResponseDto

    @GET("carts/user/{userId}")
    suspend fun getCartsByUserId(@Path("userId") userId:Int): CartsResponseDto

    @GET("users/{userId}")
    suspend fun getUserInfoById(@Path("userId") userId:Int): UserInfoResponseDto

    @PUT("users/{userId}")
    suspend fun updateUserBody(
        @Path("userId") userId:Int,
        @Body userModel:UserInfoModel
    ): UserInfoResponseDto

    @POST("carts/add")
    suspend fun addToCart(
        @Body cartRequestModel: CartRequestModel
    ) : CartAddResponseDto

    @GET("auth/me")
    suspend fun getUserDetailsByToken(@Header("Authorization") token: String): UserInfoResponseDto

    @POST("auth/refresh")
    @Headers("Content-Type: application/json")
    suspend fun refreshToken(@Body request: RefreshTokenRequestModel): RefreshTokenResponseModel

}