package com.muratdayan.epasaj.presentation.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.muratdayan.epasaj.core.common.Resource
import com.muratdayan.epasaj.domain.model.network_models.request_models.RefreshTokenRequestModel
import com.muratdayan.epasaj.domain.usecase.network_usecases.RefreshTokenUseCase
import com.muratdayan.epasaj.presentation.base.SharedPrefManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@HiltWorker
class TokenRefreshWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted val workerParams: WorkerParameters,
    private val refreshTokenUseCase: RefreshTokenUseCase
): Worker(context,workerParams) {

    override fun doWork(): Result {
        val sharedPrefManagerUserData = SharedPrefManager(context, "user_data")
        val refreshToken = sharedPrefManagerUserData.getValue("user_refresh_token", "")

        val refreshTokenRequestModel = RefreshTokenRequestModel(refreshToken)


        val job = CoroutineScope(Dispatchers.IO).launch {
            refreshTokenUseCase(refreshTokenRequestModel).collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        // Hata durumunu yönetin
                        Result.retry()
                    }
                    is Resource.Loading -> {
                        // Yükleniyor durumunu yönetin
                    }
                    is Resource.Success -> {
                        sharedPrefManagerUserData.setValue("user_token", resource.data!!.token)
                        Result.success()
                    }
                }
            }
        }

        // İşlem tamamlanana kadar bekleyin
        runBlocking {
            job.join()
        }

        return Result.success()
    }


}