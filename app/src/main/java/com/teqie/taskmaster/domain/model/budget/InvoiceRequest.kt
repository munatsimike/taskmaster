package com.teqie.taskmaster.domain.model.budget

import com.teqie.taskmaster.util.toIsoString
import java.time.LocalDate

data class InvoiceRequest(
    val id: String = "",
    val amount: String = "",
    val budgetId: String = "",
    val date: String = LocalDate.now().toIsoString(),
    val paid: String = ""
)
