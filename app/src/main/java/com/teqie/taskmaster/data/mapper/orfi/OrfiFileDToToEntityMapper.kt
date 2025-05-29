package com.teqie.taskmaster.data.mapper.orfi

import com.teqie.taskmaster.data.local.db.enties.OrfiFileEntity
import com.teqie.taskmaster.data.mapper.DtoToEntityMapper
import com.teqie.taskmaster.data.remote.dto.orfi.ORFIFilesResponseDto

object OrfiFileDToToEntityMapper: DtoToEntityMapper<ORFIFilesResponseDto, OrfiFileEntity> {
    override fun ORFIFilesResponseDto.toEntity(projectId: String?): OrfiFileEntity {
        return OrfiFileEntity(
            description = description,
            fileName = fileName,
            id = id,
            isDeleted = isDeleted,
            orfiId = orfiId,
            url = url
        )
    }
}