package com.teqie.taskmaster.data.mapper.orfi

import com.teqie.taskmaster.data.mapper.DomainToDtoMapper
import com.teqie.taskmaster.data.remote.dto.orfi.CreateUpdateORFIRequest
import com.teqie.taskmaster.ui.model.orfi.Orfi

object OrfiToDtoMapper: DomainToDtoMapper<Orfi, CreateUpdateORFIRequest> {
    override fun Orfi.toDtoModel(): CreateUpdateORFIRequest {
        return  CreateUpdateORFIRequest(
            assignedTo = assignedTo,
            dueDate = dueDate,
            projectId = projectId,
            question = question,
            resolved = resolved
        )
    }
}