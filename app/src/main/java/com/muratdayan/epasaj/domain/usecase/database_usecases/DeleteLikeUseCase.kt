package com.muratdayan.epasaj.domain.usecase.database_usecases

import com.muratdayan.epasaj.domain.model.database_models.UserLikesModel
import com.muratdayan.epasaj.domain.repository.DatabaseRepository
import javax.inject.Inject

class DeleteLikeUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository
){
    suspend operator fun invoke(userLikesModel: UserLikesModel) = databaseRepository.deleteLike(userLikesModel)

}