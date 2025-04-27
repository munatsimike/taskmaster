package com.teqie.taskmaster.data.mapper.budgetphase.invoice

import com.teqie.taskmaster.data.local.db.enties.InvoiceEntity
import com.teqie.taskmaster.data.mapper.DtoToEntityMapper
import com.teqie.taskmaster.data.remote.dto.budget.invoice.InvoiceResponseDto

object InvoiceDtoToEntityMapper : DtoToEntityMapper<InvoiceResponseDto, InvoiceEntity> {
    override fun InvoiceResponseDto.toEntity(projectId: String?): InvoiceEntity {
        return InvoiceEntity(
            amount = amount,
            assignedTo = assignedTo,
            assignedToAvatar = assignedToAvatar,
            assignedToName = assignedToName,
            assignedToUsername = assignedToUsername,
            budgetId = budgetId,
            date = date,
            id = id,
            isDeleted = isDeleted,
            paid = paid
        )
    }
}