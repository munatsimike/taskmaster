package com.teqie.taskmaster.domain.model.budget.invoices

import com.teqie.taskmaster.domain.model.teamMember.AssignedTeamMember
import kotlin.math.roundToInt

data class Invoice(
    val amount: Double,
    val assignedTeamMember: AssignedTeamMember,
    val budgetId: String,
    val date: String,
    val id: String,
    val isDeleted: Boolean,
    val paid: Double
) {
    val balance = amount - paid
    val progress = paid.toFloat() / amount.toFloat()
    val progressAsAPercentage: Int = ((paid / amount) * 100).roundToInt()
}