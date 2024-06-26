package com.muratdayan.epasaj.domain.usecase.database_usecases

import com.muratdayan.epasaj.domain.repository.DatabaseRepository
import javax.inject.Inject

class DeleteAllUserLikesUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository
){

    suspend operator fun invoke() = databaseRepository.deleteAllUserLikes()
}