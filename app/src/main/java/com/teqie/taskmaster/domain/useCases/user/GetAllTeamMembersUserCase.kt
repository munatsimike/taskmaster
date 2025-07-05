package com.teqie.taskmaster.domain.useCases.user

import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.teamMember.TeamMember
import com.teqie.taskmaster.domain.teams.TeamsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTeamMembersUserCase @Inject constructor(private val teamsRepo: TeamsRepository) {
    operator fun invoke(): Flow<Resource<List<TeamMember>>> {
        return teamsRepo.getAllTeamMembers()
    }
}