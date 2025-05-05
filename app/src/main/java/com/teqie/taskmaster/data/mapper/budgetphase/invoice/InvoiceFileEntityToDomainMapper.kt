package com.teqie.taskmaster.data.mapper.budgetphase.invoice

import com.teqie.taskmaster.data.local.db.enties.InvoiceFileEntity
import com.teqie.taskmaster.data.mapper.EntityToDomain
import com.teqie.taskmaster.ui.screen.bugdetPhase.InvoiceFile

object InvoiceFileEntityToDomainMapper: EntityToDomain<InvoiceFileEntity, InvoiceFile> {
    override fun InvoiceFileEntity.toDomainModel(): InvoiceFile {
        TODO("Not yet implemented")
    }
}