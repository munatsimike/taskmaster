package com.teqie.taskmaster.domain.useCases.orfi

import com.teqie.taskmaster.data.repository.orfi.OrfiRepository
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.orfi.ORFIFile
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrfiFilesUseCase @Inject constructor(private val orfiRepository: OrfiRepository) {
    operator fun invoke(orfiId: String): Flow<Resource<List<ORFIFile>>> {
        return orfiRepository.getORFIFiles(orfiId)
    }
}