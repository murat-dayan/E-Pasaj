package com.muratdayan.epasaj.data.locale.service

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.muratdayan.epasaj.domain.model.database_models.UserLikesModel

@Dao
interface DatabaseService {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userLikesModel: UserLikesModel)

    @Query("SELECT * FROM userlikes WHERE userId = :userId")
    suspend fun getLikesProductIdsByUserId(userId: Int): List<UserLikesModel>

    @Delete
    suspend fun deleteLike(userLikesModel: UserLikesModel)

    @Query("DELETE FROM userlikes")
    suspend fun deleteAllUsersLikes()
}