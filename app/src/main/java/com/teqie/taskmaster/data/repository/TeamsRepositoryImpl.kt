package com.teqie.taskmaster.data.repository


import com.teqie.taskmaster.data.local.LocalDataSource
import com.teqie.taskmaster.data.mapper.user.TeamsEntityToDomainMapper.toDomainList
import com.teqie.taskmaster.data.remote.RemoteDataSource
import com.teqie.taskmaster.data.repository.budget.BaseRepository
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.teamMember.TeamMember
import com.teqie.taskmaster.domain.model.user.CreateUserRequest
import com.teqie.taskmaster.domain.teams.TeamsRepository
import com.teqie.taskmaster.ui.model.ResponseMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TeamsRepositoryImpl @Inject constructor(
    val localDataSource: LocalDataSource,
    val remoteDataSource: RemoteDataSource
) : BaseRepository(), TeamsRepository {
    override suspend fun createAssignUser(createUserRequest: CreateUserRequest): Flow<Resource<ResponseMessage>> {
        return processApiResponse(
            call = { remoteDataSource.createAssignUser(createUserRequest) },
            onSuccess = { response ->
                ResponseMessage(response.message)
            })
    }

    override fun getTeamsByProject(projectId: String): Flow<Resource<List<TeamMember>>> = flow {
        try {
            localDataSource.fetchProjectTeamMembers(projectId).collect { teamMembersDto ->
                val domainList = teamMembersDto.toDomainList()
                emit(Resource.Success(domainList))
            }
        } catch (e: Exception) {
            emit(Resource.Error(Throwable("Unknown error")))
        }
    }
}