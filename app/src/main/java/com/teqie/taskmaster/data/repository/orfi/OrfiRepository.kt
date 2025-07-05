package com.teqie.taskmaster.data.repository.orfi

import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.orfi.ORFIFile
import com.teqie.taskmaster.ui.model.ResponseMessage
import com.teqie.taskmaster.ui.model.orfi.Orfi
import kotlinx.coroutines.flow.Flow

interface OrfiRepository {
    fun updateORFI(orfi: Orfi): Flow<Resource<ResponseMessage>>

    fun fetchOrfi(projectId: String): Flow<Resource<List<Orfi>>>

    fun createORFI(createORFIRequest: Orfi): Flow<Resource<ResponseMessage>>

    fun deleteORFI(orfiId: String): Flow<Resource<ResponseMessage>>

    fun syncOrfiToLocalDb(projectId: String): Flow<Resource<Unit>>

    fun getORFIFiles(orfiId: String): Flow<Resource<List<ORFIFile>>>

    fun syncOrfiFileToLocalDb(projectId: String): Flow<Resource<Unit>>

    suspend fun deleteOrfis()
}