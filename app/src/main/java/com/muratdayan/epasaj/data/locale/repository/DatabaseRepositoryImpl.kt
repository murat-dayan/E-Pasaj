package com.muratdayan.epasaj.data.locale.repository

import android.util.Log
import com.muratdayan.epasaj.core.common.Resource
import com.muratdayan.epasaj.data.locale.service.DatabaseService
import com.muratdayan.epasaj.domain.model.database_models.UserLikesModel
import com.muratdayan.epasaj.domain.repository.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

// database işlemleri
class DatabaseRepositoryImpl @Inject constructor(
    private val databaseService: DatabaseService
): DatabaseRepository {

    // insert yapılırsa true döndürür
    override suspend fun insertUserLikes(userLikesModel: UserLikesModel): Boolean {
        return try {
            databaseService.insert(userLikesModel)
            true
        }catch (e:Exception){
            false
        }
    }

    //delete yapılırsa true döndürür
    override suspend fun deleteLike(userLikesModel: UserLikesModel): Boolean {
        return try {
            databaseService.deleteLike(userLikesModel)
            Log.d("success", "deleteLike: Success")
            true
        }catch (e:Exception){
            Log.d("error", "deleteLike: ${e.message}")
            false
        }
    }

    override suspend fun deleteAllUserLikes(): Boolean {
        return try {
            databaseService.deleteAllUsersLikes()
            true
        }catch (e:Exception){
            false
        }
    }


    // apiden dönen veriye göre resource'un değerini belirler
    override fun getLikesProductIdsByUserId(userId: Int): Flow<Resource<List<UserLikesModel>>> = flow {

        emit(Resource.Loading())

        val likes = databaseService.getLikesProductIdsByUserId(userId)
        emit(Resource.Success(likes))
    }.flowOn(Dispatchers.IO)
        .catch {
            emit(Resource.Error(it.message.toString()))
        }




}