package com.teqie.taskmaster.domain.useCases.orfi

import com.teqie.taskmaster.data.repository.orfi.OrfiRepository
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.ui.model.ResponseMessage
import com.teqie.taskmaster.ui.model.orfi.Orfi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateOrfiUseCase @Inject constructor(private val orfiRepository: OrfiRepository) {
    operator fun invoke(orfi: Orfi): Flow<Resource<ResponseMessage>> {
        return orfiRepository.updateORFI(orfi)
    }
}