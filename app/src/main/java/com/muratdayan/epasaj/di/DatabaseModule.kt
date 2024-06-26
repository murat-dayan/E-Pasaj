package com.muratdayan.epasaj.di

import android.content.Context
import androidx.room.Room
import com.muratdayan.epasaj.core.common.Constants
import com.muratdayan.epasaj.data.locale.repository.DatabaseRepositoryImpl
import com.muratdayan.epasaj.data.locale.service.DatabaseService
import com.muratdayan.epasaj.data.locale.service.EPasajDatabase
import com.muratdayan.epasaj.domain.repository.DatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): EPasajDatabase {
        return Room.databaseBuilder(
            context,
            EPasajDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideDatabaseService(database: EPasajDatabase) : DatabaseService {
        return  database.ePasajService()
    }

    @Provides
    @Singleton
    fun provideDatabaseRepository(databaseService: DatabaseService) : DatabaseRepository {
        return DatabaseRepositoryImpl(databaseService)
    }


}