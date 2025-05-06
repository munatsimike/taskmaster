package com.teqie.taskmaster.domain.teams

import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.teamMember.TeamMember
import com.teqie.taskmaster.domain.model.user.CreateUserRequest
import com.teqie.taskmaster.ui.model.ResponseMessage
import kotlinx.coroutines.flow.Flow

interface TeamsRepository {
    suspend fun createAssignUser(createUserRequest: CreateUserRequest): Flow<Resource<ResponseMessage>>
    fun getTeamsByProject(projectId: String): Flow<Resource<List<TeamMember>>>
    fun syncTeamsToLocalDb(projectId: String): Flow<Resource<Unit>>
}