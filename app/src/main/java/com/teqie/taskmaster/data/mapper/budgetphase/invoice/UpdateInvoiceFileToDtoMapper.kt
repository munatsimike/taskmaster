package com.teqie.taskmaster.data.mapper.budgetphase.invoice

import com.teqie.taskmaster.data.mapper.DomainToDtoMapper
import com.teqie.taskmaster.data.remote.dto.file.UpdateFileRequestDTo
import com.teqie.taskmaster.ui.screen.bugdetPhase.InvoiceFile

object UpdateInvoiceFileToDtoMapper: DomainToDtoMapper<InvoiceFile, UpdateFileRequestDTo> {
    override fun InvoiceFile.toDtoModel(): UpdateFileRequestDTo {
        TODO("Not yet implemented")
    }
}