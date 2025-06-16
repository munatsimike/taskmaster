package com.teqie.taskmaster.data.mapper

import com.teqie.taskmaster.data.remote.dto.gallery.SaveImageRequestDto
import com.teqie.taskmaster.domain.model.file.FileData

object FileToSaveImageDto {
    fun toDtoModel(fileData: FileData): SaveImageRequestDto {
        return SaveImageRequestDto(
            folderId = fileData.id,
            name = fileData.fileName,
            projectId = fileData.projectId,
            tags = fileData.tags,
            url = fileData.url
        )
    }
}