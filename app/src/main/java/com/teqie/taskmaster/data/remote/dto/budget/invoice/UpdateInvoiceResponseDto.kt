package com.teqie.taskmaster.data.remote.dto.budget.invoice

import com.teqie.taskmaster.domain.model.RemoteResponse
import kotlinx.serialization.Serializable

@Serializable
class UpdateInvoiceResponseDto(
    val invoice: InvoiceResponseDto,
    override val message: String
): RemoteResponse