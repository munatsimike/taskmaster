package com.teqie.taskmaster.data.mapper.orfi

import com.teqie.taskmaster.data.local.db.enties.OrfiEntity
import com.teqie.taskmaster.data.mapper.DtoToEntityMapper
import com.teqie.taskmaster.data.remote.dto.orfi.OrfiDto

object OrfiDtoToEntityMapper: DtoToEntityMapper<OrfiDto, OrfiEntity> {
    override fun OrfiDto.toEntity(projectId: String?): OrfiEntity {
        return OrfiEntity(
            assignedAvatar = assignedAvatar,
            assignedName = assignedName,
            assignedTo = assignedTo,
            assignedUserName = assignedUserName,
            createdAt = createdAt,
            dueDate = dueDate,
            id = id,
            isDeleted = isDeleted == 0 ,
            projectId = this.projectId,
            question = question,
            resolved = resolved == 0,
            updatedAt = updatedAt
        )
    }
}