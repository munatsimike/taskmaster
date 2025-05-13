package com.teqie.taskmaster.data.mapper

import com.teqie.taskmaster.data.remote.dto.file.AddFileRequestDto
import com.teqie.taskmaster.domain.model.file.FileData

object FileToAddFileRequestDtoMapper: DomainToDtoMapper<FileData, AddFileRequestDto> {
    override fun FileData.toDtoModel(): AddFileRequestDto {
       return AddFileRequestDto(
           description = description,
           fileName = fileName,
           orfi_id = id,
           invoice_id = id,
           url = url
       )
    }
}