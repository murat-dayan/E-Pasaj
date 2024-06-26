package com.muratdayan.epasaj.di

import com.muratdayan.epasaj.core.common.Constants
import com.muratdayan.epasaj.data.remote.repository.NetworkRepositoryImpl
import com.muratdayan.epasaj.data.remote.service.NetworkService
import com.muratdayan.epasaj.domain.repository.NetworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofitInstance(okHttpClient: OkHttpClient) : Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .baseUrl(Constants.BASE_URL)
        .build()

    @Provides
    @Singleton
    fun providesNetworkService(retrofit: Retrofit): NetworkService= retrofit.create(NetworkService::class.java)

    @Provides
    @Singleton
    fun providesNetworkRepository(networkService: NetworkService): NetworkRepository{
        return NetworkRepositoryImpl(networkService)
    }


}