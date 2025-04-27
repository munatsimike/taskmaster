package com.teqie.taskmaster.domain.model.budget.invoices

import com.teqie.taskmaster.util.toIsoString
import java.time.LocalDate

data class CreateInvoiceRequest(
    val id: String = "",
    val amount: String = "",
    val budgetId: String = "",
    val date: String = LocalDate.now().toIsoString(),
    val paid: String = ""
)
