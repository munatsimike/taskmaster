package com.teqie.taskmaster.data.remote

import com.teqie.taskmaster.data.remote.dto.ProjectResponseDto
import com.teqie.taskmaster.data.remote.dto.auth.LoginRequestDto
import com.teqie.taskmaster.data.remote.dto.budget.BudgetPhaseResponseDto
import com.teqie.taskmaster.data.remote.dto.budget.CreateBudgetPhaseDto
import com.teqie.taskmaster.data.remote.dto.budget.CreateBudgetPhaseResponse
import com.teqie.taskmaster.data.remote.dto.budget.UpdateBudgetPhaseDto
import com.teqie.taskmaster.data.remote.dto.budget.UpdateBudgetPhaseResponseDto
import com.teqie.taskmaster.data.remote.dto.budget.invoice.CreateInvoiceRequestDto
import com.teqie.taskmaster.data.remote.dto.budget.invoice.CreateInvoiceResponse
import com.teqie.taskmaster.data.remote.dto.budget.invoice.InvoiceResponseDto
import com.teqie.taskmaster.data.remote.dto.budget.invoice.UpdateInvoiceResponseDto
import com.teqie.taskmaster.data.remote.dto.dashboard.DashboardAPiResponseDto
import com.teqie.taskmaster.data.remote.dto.user.UserApiResponseDto
import com.teqie.taskmaster.domain.model.RemoteResponse
import com.teqie.taskmaster.domain.model.project.Project
import com.teqie.taskmaster.ui.model.ResponseMessage
import retrofit2.Response

interface RemoteDataSource {

    suspend fun login(loginRequest: LoginRequestDto): Response<UserApiResponseDto>

    suspend fun getProjects(): Response<List<ProjectResponseDto>>

    suspend fun addOrEditNewProject(
        projectRequest: Project,
        isEditing: Boolean
    ): Response<out RemoteResponse>

    suspend fun deleteProject(projectId: String): Response<ResponseMessage>

    suspend fun getDashboard(projectId: String): Response<DashboardAPiResponseDto>

    suspend fun getBudgetPhases(projectId: String): Response<List<BudgetPhaseResponseDto>>

    suspend fun updateBudgetPhase(updateBudgetPhase: UpdateBudgetPhaseDto): Response<UpdateBudgetPhaseResponseDto>

    suspend fun createBudgetPhase(budgetPhase: CreateBudgetPhaseDto): Response<CreateBudgetPhaseResponse>

    suspend fun deleteBudgetPhase(budgetId: String): Response<ResponseMessage>

    suspend fun getInvoicesByBudgetId(budgetId: String): Response<List<InvoiceResponseDto>>

    suspend fun createBudgetInvoice(createInvoiceRequestDto: CreateInvoiceRequestDto): Response<CreateInvoiceResponse>

    suspend fun deleteInvoice(invoiceId: String): Response<ResponseMessage>

    suspend fun updateInvoice(invoiceId: String,updateInvoiceRequestDto: CreateInvoiceRequestDto): Response<UpdateInvoiceResponseDto>
}