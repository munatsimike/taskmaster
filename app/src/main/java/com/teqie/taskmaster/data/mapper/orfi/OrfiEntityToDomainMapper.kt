package com.teqie.taskmaster.data.mapper.orfi

import com.teqie.taskmaster.data.local.db.enties.OrfiEntity
import com.teqie.taskmaster.data.mapper.EntityToDomain
import com.teqie.taskmaster.data.mapper.orfi.OrfiDtoToEntityMapper.toEntity
import com.teqie.taskmaster.ui.model.orfi.Orfi

object OrfiEntityToDomainMapper: EntityToDomain<OrfiEntity, Orfi> {
    override fun OrfiEntity.toDomainModel(): Orfi {
        return Orfi(
            assignedAvatar = assignedAvatar,
            assignedName = assignedName,
            assignedTo = assignedTo,
            assignedUserName = assignedUserName,
            createdAt = createdAt,
            dueDate = dueDate,
            id = id,
            isDeleted = isDeleted ,
            projectId = this.projectId,
            question = question,
            resolved = resolved,
            updatedAt = updatedAt
        )
    }
}