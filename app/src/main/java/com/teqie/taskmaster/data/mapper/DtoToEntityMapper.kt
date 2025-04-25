package com.teqie.taskmaster.data.mapper

/**
 * Maps a DTO to its corresponding Room entity.
 *
 * @param projectId Optional project ID used only in specific cases (e.g., mapping Dashboard DTOs
 *                  where the project ID is not included in the response and must be passed manually).
 */
interface DtoToEntityMapper <DtoModel, EntityModel>{
   fun DtoModel.toEntity(projectId: String? = null): EntityModel

    fun List<DtoModel>.toEntityList(projectId: String? = null): List<EntityModel>{
        return this.map { it.toEntity(projectId) }
    }
}