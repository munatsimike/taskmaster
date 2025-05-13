package com.teqie.taskmaster.data.mapper.budgetphase.invoice

import com.teqie.taskmaster.data.mapper.DomainToDtoMapper
import com.teqie.taskmaster.data.remote.dto.file.UpdateFileRequestDTo
import com.teqie.taskmaster.domain.model.InvoiceFile

object InvoiceFileToDtoMapper: DomainToDtoMapper<InvoiceFile, UpdateFileRequestDTo> {
    override fun InvoiceFile.toDtoModel(): UpdateFileRequestDTo {
       return UpdateFileRequestDTo(
           id = id,
           fileName = fileName,
           description = description
       )
    }
}