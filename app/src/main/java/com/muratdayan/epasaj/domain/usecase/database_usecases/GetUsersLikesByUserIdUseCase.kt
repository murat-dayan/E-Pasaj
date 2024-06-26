package com.muratdayan.epasaj.domain.usecase.database_usecases

import com.muratdayan.epasaj.domain.repository.DatabaseRepository
import javax.inject.Inject

class GetUsersLikesByUserIdUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository
){

    operator fun invoke(userId: Int) = databaseRepository.getLikesProductIdsByUserId(userId)

}