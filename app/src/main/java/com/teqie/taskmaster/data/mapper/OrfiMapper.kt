package com.teqie.taskmaster.data.mapper

import com.teqie.taskmaster.data.remote.dto.orfi.OrfiResponseDto
import com.teqie.taskmaster.ui.model.orfi.Orfi

object OrfiMapper {

    private fun OrfiResponseDto.toOrfiModel(): Orfi {
        return Orfi(
            assignedAvatar = this.assignedAvatar, // Depending on use case, you might want to handle avatar differently
            assignedName = this.assignedName,
            assignedTo = this.assignedTo,
            assignedUserName = this.assignedUserName,
            createdAt = this.createdAt,  // Consider using a Date type if appropriate
            dueDate = this.dueDate,    // Consider using a Date type if appropriate
            id = this.id,
            isDeleted = this.isDeleted == 1, // Changed to Boolean for clarity in the domain model
            projectId = this.projectId,
            question = this.question,
            resolved = this.resolved == 1,  // Changed to Boolean for clarity
            updatedAt = this.updatedAt   // Consider using a
        )
    }
    fun List<OrfiResponseDto>.toLstOfOrfiModel(): List<Orfi> {
        return this.map {
            it.toOrfiModel()
        }
    }
}