package com.teqie.taskmaster.data.remote.api.service

import com.teqie.taskmaster.data.remote.dto.budget.BudgetPhaseResponseDto
import com.teqie.taskmaster.data.remote.dto.budget.CreateBudgetPhaseDto
import com.teqie.taskmaster.data.remote.dto.budget.CreateBudgetPhaseResponse
import com.teqie.taskmaster.data.remote.dto.budget.CreateFileDto
import com.teqie.taskmaster.data.remote.dto.budget.CreateInvoiceFileResponse
import com.teqie.taskmaster.data.remote.dto.budget.UpdateBudgetPhaseDto
import com.teqie.taskmaster.data.remote.dto.budget.UpdateBudgetPhaseResponseDto
import com.teqie.taskmaster.data.remote.dto.budget.invoice.CreateInvoiceResponse
import com.teqie.taskmaster.data.remote.dto.budget.invoice.CrreateInvoicetDto
import com.teqie.taskmaster.data.remote.dto.budget.invoice.InvoiceResponseDto
import com.teqie.taskmaster.data.remote.dto.budget.invoice.UpdateInvoiceResponseDto
import com.teqie.taskmaster.data.remote.dto.budget.invoice.file.InvoiceFileResponseDto
import com.teqie.taskmaster.data.remote.dto.budget.invoice.file.UpdateFileDto
import com.teqie.taskmaster.ui.model.ResponseMessage
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface BudgetService {
    @GET("api/budget-phases/project/{id}")
    suspend fun getBudgets(@Path("id") projectId: String): Response<List<BudgetPhaseResponseDto>>

    @GET("api/budget-invoices/budget/{id}")
    suspend fun getInvoicesByBudgetId(@Path("id") budgetId: String): Response<List<InvoiceResponseDto>>

    @GET("api/invoice-files/budget-invoice/{id}")
    suspend fun getInvoiceFiles(@Path("id")invoiceId: String): Response<List<InvoiceFileResponseDto>>

    @PUT("api/budget-invoices/{id}")
    suspend fun updateBudgetInvoice(@Path("id")invoiceId: String, @Body invoiceUpdateRequest: CrreateInvoicetDto) : Response<UpdateInvoiceResponseDto>

    @POST("api/budget-phases")
    suspend fun createNewBudgetPhase(@Body newBudgetPhaseDto: CreateBudgetPhaseDto): Response<CreateBudgetPhaseResponse>

    @DELETE("api/budget-phases/{id}")
    suspend fun deleteBudgetPhase(@Path("id")budgetId: String): Response<ResponseMessage>

    @DELETE("api/budget-invoices/{id}")
    suspend fun deleteInvoice(@Path("id")invoiceId: String): Response<ResponseMessage>

    @POST("api/budget-invoices")
    suspend fun createBudgetInvoice(@Body createInvoiceRequest: CrreateInvoicetDto) : Response<CreateInvoiceResponse>

    @PUT("api/invoice-files/{id}")
    suspend fun updateBudgetInvoiceFile(@Path("id")invoiceId: String, @Body invoiceRequestDto: UpdateFileDto): Response<ResponseMessage>

    @DELETE("api/invoice-files/{id}")
    suspend fun deleteInvoiceFile(@Path("id")invoiceFileId: String): Response<ResponseMessage>

    @POST("api/invoice-files")
    suspend fun addNewInvoiceFile(@Body invoiceFileRequestDto: CreateFileDto): Response<CreateInvoiceFileResponse>

    @PUT("api/budget-phases/{id}")
    suspend fun updateBudgetPhase(@Path("id")phaseId: String, @Body updateBudgetPhase: UpdateBudgetPhaseDto) : Response<UpdateBudgetPhaseResponseDto>
}