package com.teqie.taskmaster.data.mapper.gallery

import com.teqie.taskmaster.data.mapper.DomainToDtoMapper
import com.teqie.taskmaster.data.remote.dto.gallery.AddFolderRequestDto
import com.teqie.taskmaster.domain.gallery.FolderState

object FolderDomainToDtoMapper: DomainToDtoMapper<FolderState, AddFolderRequestDto> {
    override fun FolderState.toDtoModel(): AddFolderRequestDto {
        return  AddFolderRequestDto(
            name = name,
            description = description,
            projectId = projectId
        )
    }
}