package com.teqie.taskmaster.domain.useCases.user

import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.teamMember.TeamMember
import com.teqie.taskmaster.domain.teams.TeamsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTeamsByProjectUseCase @Inject constructor(private val teamsRepo: TeamsRepository) {
    operator fun invoke(projectId: String): Flow<Resource<List<TeamMember>>> {
        return teamsRepo.getTeamsByProject(projectId)
    }
}