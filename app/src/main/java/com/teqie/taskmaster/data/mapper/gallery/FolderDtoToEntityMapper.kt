package com.teqie.taskmaster.data.mapper.gallery

import com.teqie.taskmaster.data.local.db.enties.FolderEntity
import com.teqie.taskmaster.data.mapper.DtoToEntityMapper
import com.teqie.taskmaster.data.remote.dto.gallery.FoldersResponseDto

object FolderDtoToEntityMapper : DtoToEntityMapper<FoldersResponseDto, FolderEntity> {
    override fun FoldersResponseDto.toEntity(projectId: String?): FolderEntity {
        return FolderEntity(
            description = description,
            id = id,
            isDeleted = isDeleted == 0,
            name = name,
            projectId = this.projectId
        )
    }
}