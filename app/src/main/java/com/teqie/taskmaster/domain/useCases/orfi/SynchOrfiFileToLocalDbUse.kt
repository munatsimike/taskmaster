package com.teqie.taskmaster.domain.useCases.orfi

import com.teqie.taskmaster.data.repository.orfi.OrfiRepository
import com.teqie.taskmaster.domain.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SynchOrfiFileToLocalDbUse @Inject constructor(private val orfiRepository: OrfiRepository){
    operator fun invoke(projectId: String): Flow<Resource<Unit>> {
        return orfiRepository.syncOrfiFileToLocalDb(projectId = projectId)
    }
}