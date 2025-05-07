package com.teqie.taskmaster.domain.useCases.user

import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.teams.TeamsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SyncTeamsToLocalDbUseCase @Inject constructor(private val teamsRepository: TeamsRepository) {
    operator fun invoke(projectId: String): Flow<Resource<Unit>> {
        return teamsRepository.syncTeamsToLocalDb(projectId)
    }
}
