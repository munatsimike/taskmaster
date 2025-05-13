package com.teqie.taskmaster.data.mapper.budgetphase.invoice

import com.teqie.taskmaster.data.local.db.enties.InvoiceFileEntity
import com.teqie.taskmaster.data.mapper.DtoToEntityMapper
import com.teqie.taskmaster.data.remote.dto.file.InvoiceFileDtoItem

object InvoiceFileDtoToEntity : DtoToEntityMapper<InvoiceFileDtoItem, InvoiceFileEntity> {
    override fun InvoiceFileDtoItem.toEntity(projectId: String?): InvoiceFileEntity {
        return InvoiceFileEntity(
            createdAt = createdAt,
            description = description,
            fileName = fileName,
            id = id,
            invoiceId = invoiceId,
            isDeleted = isDeleted,
            updatedAt = updatedAt,
            url = url
        )
    }
}