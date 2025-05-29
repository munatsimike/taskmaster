package com.teqie.taskmaster.data.mapper.budgetphase.invoice

import com.teqie.taskmaster.data.local.db.enties.InvoiceFileEntity
import com.teqie.taskmaster.data.mapper.EntityToDomain
import com.teqie.taskmaster.domain.model.InvoiceFile
import com.teqie.taskmaster.domain.model.file.FileType

object InvoiceFileEntityToDomainMapper : EntityToDomain<InvoiceFileEntity, InvoiceFile> {
    override fun InvoiceFileEntity.toDomainModel(): InvoiceFile {
        return InvoiceFile(
            createdAt = createdAt,
            invoiceId = invoiceId,
            updatedAt = updatedAt,
            fileType = FileType.InvoiceFile,
            description = description,
            fileName = fileName,
            id = id,
            isDeleted = false,
            url = url
        )
    }
}