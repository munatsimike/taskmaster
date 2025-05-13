package com.teqie.taskmaster.data.remote

import com.teqie.taskmaster.data.remote.dto.ProjectResponseDto
import com.teqie.taskmaster.data.remote.dto.auth.LoginRequestDto
import com.teqie.taskmaster.data.remote.dto.budget.BudgetPhaseResponseDto
import com.teqie.taskmaster.data.remote.dto.budget.CreateBudgetPhaseDto
import com.teqie.taskmaster.data.remote.dto.budget.CreateBudgetPhaseResponse
import com.teqie.taskmaster.data.remote.dto.budget.CreateInvoiceFileResponse
import com.teqie.taskmaster.data.remote.dto.budget.UpdateBudgetPhaseDto
import com.teqie.taskmaster.data.remote.dto.budget.UpdateBudgetPhaseResponseDto
import com.teqie.taskmaster.data.remote.dto.budget.invoice.CreateInvoiceRequestDto
import com.teqie.taskmaster.data.remote.dto.budget.invoice.CreateInvoiceResponse
import com.teqie.taskmaster.data.remote.dto.budget.invoice.InvoiceResponseDto
import com.teqie.taskmaster.data.remote.dto.budget.invoice.UpdateInvoiceResponseDto
import com.teqie.taskmaster.data.remote.dto.dashboard.DashboardAPiResponseDto
import com.teqie.taskmaster.data.remote.dto.file.AddFileRequestDto
import com.teqie.taskmaster.data.remote.dto.file.InvoiceFileDtoItem
import com.teqie.taskmaster.data.remote.dto.file.PreSignedUrlResponseDto
import com.teqie.taskmaster.data.remote.dto.file.UpdateFileRequestDTo
import com.teqie.taskmaster.data.remote.dto.user.CreateUserResponseDto
import com.teqie.taskmaster.data.remote.dto.user.TeamsResponseItemDto
import com.teqie.taskmaster.data.remote.dto.user.UserApiResponseDto
import com.teqie.taskmaster.domain.model.RemoteResponse
import com.teqie.taskmaster.domain.model.file.PresignedUrl
import com.teqie.taskmaster.domain.model.project.Project
import com.teqie.taskmaster.domain.model.user.CreateUserRequest
import com.teqie.taskmaster.ui.model.ResponseMessage
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.File

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

    suspend fun updateInvoice(
        invoiceId: String,
        updateInvoiceRequestDto: CreateInvoiceRequestDto
    ): Response<UpdateInvoiceResponseDto>

    suspend fun getInvoiceFile(invoiceId: String): Response<List<InvoiceFileDtoItem>>

    suspend fun updateInvoiceFile(updateFileRequestDTo: UpdateFileRequestDTo): Response<ResponseMessage>

   // suspend fun updateORFIFile(addEditFileRequestDto: AddFileRequestDto): Response<CreateEditOrfiFileResponse>

    suspend fun downloadFile(fileUrl: String): Response<ResponseBody>

    suspend fun addInvoiceFile(invoiceFileRequestDto: AddFileRequestDto): Response<CreateInvoiceFileResponse>

    suspend fun getPreSignedUrl(
        fileName: String,
        fileType: String
    ): Response<PreSignedUrlResponseDto>

    //suspend fun deleteORFIFile(orfiFileId: String): Response<ResponseMessage>

    fun uploadFileToPreSignedUrl(file: File, preSignedUrl: PresignedUrl): String

    suspend fun getTeamsByProject(projectId: String): Response<List<TeamsResponseItemDto>>

    suspend fun createAssignUser(createUserRequest: CreateUserRequest): Response<CreateUserResponseDto>
    fun updateORFIFile(toInvoiceFileRequestDto: Any): Any

}
