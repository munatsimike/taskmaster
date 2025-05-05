package com.teqie.taskmaster.data.mapper.budgetphase.invoice

import com.teqie.taskmaster.data.mapper.DomainToDtoMapper
import com.teqie.taskmaster.data.remote.dto.file.AddEditFileRequestDto
import com.teqie.taskmaster.domain.file.FileData

object AddInvoiceFileToDtoMapper: DomainToDtoMapper<FileData, AddEditFileRequestDto> {
    override fun FileData.toDtoModel(): AddEditFileRequestDto {
        TODO("Not yet implemented")
    }
}