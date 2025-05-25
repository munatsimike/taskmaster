package com.teqie.taskmaster.data.repository.orfi

import com.teqie.taskmaster.data.local.db.enties.OrfiEntity
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.ui.model.ResponseMessage
import com.teqie.taskmaster.ui.model.orfi.Orfi
import kotlinx.coroutines.flow.Flow

interface OrfiRepository {
    fun updateORFI(orfi: Orfi): Flow<Resource<ResponseMessage>>

    fun getORFI(projectId: String): Flow<Resource<List<OrfiEntity>>>

    fun createORFI(createORFIRequest: Orfi): Flow<Resource<ResponseMessage>>

    fun deleteORFI(orfiId: String): Flow<Resource<ResponseMessage>>
}