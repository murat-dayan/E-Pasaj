package com.muratdayan.epasaj.domain.repository

import com.muratdayan.epasaj.core.common.Resource
import com.muratdayan.epasaj.domain.model.database_models.UserLikesModel
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {

    suspend fun insertUserLikes(userLikesModel: UserLikesModel) : Boolean

    fun getLikesProductIdsByUserId(userId: Int): Flow<Resource<List<UserLikesModel>>>

    suspend fun deleteLike(userLikesModel: UserLikesModel) : Boolean

    suspend fun deleteAllUserLikes(): Boolean


}