package com.muratdayan.epasaj.data.locale.service

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muratdayan.epasaj.domain.model.database_models.UserLikesModel

// database döndürür
@Database(entities = [UserLikesModel::class], version = 2, exportSchema = false)
abstract class EPasajDatabase : RoomDatabase() {
    abstract  fun ePasajService() : DatabaseService
}