package com.muratdayan.epasaj.domain.model.database_models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "userlikes", indices = [Index(value = ["productId", "userId"], unique = true)])
data class UserLikesModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    val productId: Int,
    val userId: Int,
)
