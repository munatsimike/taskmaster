package com.teqie.taskmaster.data.mapper.budgetphase.invoice

import com.teqie.taskmaster.data.local.db.enties.InvoiceEntity
import com.teqie.taskmaster.data.mapper.EntityToDomain
import com.teqie.taskmaster.domain.model.budget.invoices.Invoice
import com.teqie.taskmaster.domain.model.teamMember.AssignedTeamMember

object InvoiceEntityToDomainMapper: EntityToDomain<InvoiceEntity, Invoice>{
    override fun InvoiceEntity.toDomainModel(): Invoice {
      return Invoice(
          amount = amount,
          assignedTeamMember = AssignedTeamMember(
              userId = null,
              assignedToUsername = assignedToUsername,
              assignedToName = assignedToName,
              assignedToAvatar = assignedToAvatar
          ),
          budgetId = budgetId,
          date = date,
          id = id,
          isDeleted = isDeleted == 0,
          paid = paid
      )
    }
}