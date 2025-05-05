package com.teqie.taskmaster.domain.useCases.user

import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.user.CreateUserRequest
import com.teqie.taskmaster.domain.teams.TeamsRepository
import com.teqie.taskmaster.ui.model.ResponseMessage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(private val teamsRepo: TeamsRepository) {
    suspend operator fun invoke(createUserRequest: CreateUserRequest): Flow<Resource<ResponseMessage>> {
        return teamsRepo.createAssignUser(createUserRequest)
    }
}
