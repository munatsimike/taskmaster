package com.teqie.taskmaster.data.mapper.budgetphase.invoice

import com.teqie.taskmaster.data.local.db.enties.InvoiceFileEntity
import com.teqie.taskmaster.data.mapper.EntityToDomain
import com.teqie.taskmaster.domain.model.InvoiceFile


object InvoiceFileEntityToDomainMapper: EntityToDomain<InvoiceFileEntity, InvoiceFile> {
    override fun InvoiceFileEntity.toDomainModel(): InvoiceFile {
        return InvoiceFile(
        )
    }
}