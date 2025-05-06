package com.teqie.taskmaster.data.remote

import com.teqie.taskmaster.data.mapper.project.ProjectDomainToDtoMapper.toCreateNewProjectRequest
import com.teqie.taskmaster.data.mapper.project.ProjectDomainToDtoMapper.toUpdateProjectRequestDto
import com.teqie.taskmaster.data.remote.api.service.AuthService
import com.teqie.taskmaster.data.remote.api.service.BudgetPhaseService
import com.teqie.taskmaster.data.remote.api.service.DashboardService
import com.teqie.taskmaster.data.remote.api.service.FileManagerService
import com.teqie.taskmaster.data.remote.api.service.ProjectService
import com.teqie.taskmaster.data.remote.api.service.TeamService
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
import com.teqie.taskmaster.data.remote.dto.file.AddEditFileRequestDto
import com.teqie.taskmaster.data.remote.dto.file.InvoiceFileDtoItem
import com.teqie.taskmaster.data.remote.dto.file.PreSignedUrlResponseDto
import com.teqie.taskmaster.data.remote.dto.file.UpdateFileRequestDTo
import com.teqie.taskmaster.data.remote.dto.user.CreateUserResponseDto
import com.teqie.taskmaster.data.remote.dto.user.TeamsResponseItemDto
import com.teqie.taskmaster.data.remote.dto.user.UserApiResponseDto
import com.teqie.taskmaster.di.UploadClient
import com.teqie.taskmaster.domain.file.PresignedUrl
import com.teqie.taskmaster.domain.model.RemoteResponse
import com.teqie.taskmaster.domain.model.project.Project
import com.teqie.taskmaster.domain.model.user.CreateUserRequest
import com.teqie.taskmaster.ui.model.ResponseMessage
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

/**
 *  * RemoteDataSourceImpl is responsible for interacting with the remote server to access and manage data.
 *  * It acts as a bridge between the data service (Retrofit interface) and the repository layer,
 */
class RemoteDataSourceImpl @Inject constructor(
    private val authService: AuthService,
    private val projectService: ProjectService,
    private val dashboardService: DashboardService,
    private val budgetPhaseService: BudgetPhaseService,
    private val teamService: TeamService,
    @UploadClient private val fileManagerService: FileManagerService,
    @UploadClient private val uploadOkHttpClient: OkHttpClient
) : RemoteDataSource {
    /**dataService
     * attempts to login through the data service and returns server response wrapped in a response object
     * @param loginRequest contains the username and password
     */
    override suspend fun login(loginRequest: LoginRequestDto): Response<UserApiResponseDto> {
        // make api request using retrofit service and returns the server response
        return authService.login(loginRequest)
    }

    // The following functions implement Create, Read, Update, and Delete (CRUD) operations for Project entities in the remote data source.
    override suspend fun getProjects(): Response<List<ProjectResponseDto>> {
        return projectService.getProjects()
    }

    override suspend fun addOrEditNewProject(
        projectRequest: Project,
        isEditing: Boolean
    ): Response<out RemoteResponse> {
        if (isEditing) {
            val updateRequest = projectRequest.toUpdateProjectRequestDto()
            return projectService.updateProject(updateRequest.id, updateRequest)

        }
        return projectService.createNewProject(projectRequest.toCreateNewProjectRequest())
    }

    override suspend fun deleteProject(projectId: String): Response<ResponseMessage> {
        return projectService.deleteProject(projectId)
    }

    override suspend fun getDashboard(projectId: String): Response<DashboardAPiResponseDto> {
        return dashboardService.getDashboard(projectId)
    }

    // The following functions implement Create, Read, Update, and Delete (CRUD) operations for budget phase entities in the remote data source.
    override suspend fun getBudgetPhases(projectId: String): Response<List<BudgetPhaseResponseDto>> {
        return budgetPhaseService.getBudgets(projectId)
    }

    override suspend fun updateBudgetPhase(updateBudgetPhase: UpdateBudgetPhaseDto): Response<UpdateBudgetPhaseResponseDto> {
        return budgetPhaseService.updateBudgetPhase(
            phaseId = updateBudgetPhase.id,
            updateBudgetPhase = updateBudgetPhase
        )
    }

    override suspend fun createBudgetPhase(budgetPhase: CreateBudgetPhaseDto): Response<CreateBudgetPhaseResponse> {
        return budgetPhaseService.createNewBudgetPhase(budgetPhase)
    }

    override suspend fun deleteBudgetPhase(budgetId: String): Response<ResponseMessage> =
        budgetPhaseService.deleteBudgetPhase(budgetId)

    override suspend fun getInvoicesByBudgetId(budgetId: String): Response<List<InvoiceResponseDto>> =
        budgetPhaseService.getInvoicesByBudgetId(budgetId)

    override suspend fun createBudgetInvoice(createInvoiceRequestDto: CreateInvoiceRequestDto): Response<CreateInvoiceResponse> =
        budgetPhaseService.createBudgetInvoice(createInvoiceRequestDto)

    override suspend fun deleteInvoice(invoiceId: String): Response<ResponseMessage> =
        budgetPhaseService.deleteInvoice(invoiceId)

    override suspend fun updateInvoice(
        invoiceId: String,
        updateInvoiceRequestDto: CreateInvoiceRequestDto
    ): Response<UpdateInvoiceResponseDto> {
        return budgetPhaseService.updateBudgetInvoice(invoiceId, updateInvoiceRequestDto)
    }

    override suspend fun getInvoiceFile(invoiceId: String): Response<List<InvoiceFileDtoItem>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateInvoiceFile(invoiceRequestDto: UpdateFileRequestDTo): Response<ResponseMessage> {
        TODO("Not yet implemented")
    }

    override suspend fun downloadFile(fileUrl: String): Response<ResponseBody> {
        TODO("Not yet implemented")
    }

    override suspend fun addInvoiceFile(invoiceFileRequestDto: AddEditFileRequestDto): Response<CreateInvoiceFileResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getPreSignedUrl(
        fileName: String,
        fileType: String
    ): Response<PreSignedUrlResponseDto> {
        TODO("Not yet implemented")
    }

    override fun uploadFileToPreSignedUrl(file: File, preSignedUrl: PresignedUrl): String {
        TODO("Not yet implemented")
    }

    override suspend fun getTeamsByProject(projectId: String): Response<List<TeamsResponseItemDto>> =
        teamService.getTeamsByProject(projectId)

    override suspend fun createAssignUser(createUserRequest: CreateUserRequest): Response<CreateUserResponseDto> {
        return teamService.createAssignUser(createUserRequest)
    }

    /**
    // file operations

    override suspend fun updateInvoiceFile(invoiceRequestDto: UpdateFileRequestDTo): Response<ResponseMessage> {
    return budgetPhaseService.updateBudgetInvoiceFile(
    invoiceRequestDto.id,
    invoiceRequestDto
    )
    }

    override suspend fun updateORFIFile(addEditFileRequestDto: AddEditFileRequestDto): Response<CreateEditOrfiFileResponse> {
    return orfiService.updateORFIFile(addEditFileRequestDto.orfi_id, addEditFileRequestDto)
    }

    override suspend fun downloadFile(fileUrl: String): Response<ResponseBody> {
    return fileManagerService.donwloadFile(fileUrl)
    }

    override suspend fun addInvoiceFile(invoiceFileRequestDto: AddEditFileRequestDto): Response<AddInvoiceFileResponse> {
    return budgetPhaseService.addNewInvoiceFile(invoiceFileRequestDto)
    }


    override suspend fun getPreSignedUrl(
    fileName: String,
    fileType: String
    ): Response<PreSignedUrlResponseDto> {
    return fileManagerService.getPreSignedUrl(fileName, fileType)
    }

    override suspend fun deleteORFIFile(orfiFileId: String): Response<ResponseMessage> {
    return orfiService.deleteORFIFile(orfiFileId)
    }

    override fun uploadFileToPreSignedUrl(file: File, preSignedUrl: PresignedUrl): String {
    val requestBody =
    file.asRequestBody("application/octet-stream".toMediaType())//MIME type is only for the request body, not headers

    val request = Request.Builder()
    .url(preSignedUrl.url)
    .put(requestBody)
    .addHeader("Content-Type", "application/octet-stream")
    .addHeader("x-amz-acl", "public-read")
    .build()

    // Execute the request
    uploadOkHttpClient.newCall(request).execute().use { response ->
    if (!response.isSuccessful) {
    // Log response details for debugging
    Timber.tag("FileUpload").e("Upload failed with response code: ${response.code}")
    Timber.tag("FileUpload").e("Response message: ${response.message}")
    response.body?.string()?.let { Timber.tag("FileUpload").e("Response body: $it") }
    throw Exception("Failed to upload file: ${response.message}")
    } else {
    Timber.tag("FileUpload").i("Upload successful with response code: ${response.code}")
    return preSignedUrl.url.substringBefore("?")
    }
    }
    }

    override suspend fun getInvoiceFile(invoiceId: String): Response<List<InvoiceFileDtoItem>> =
    budgetPhaseService.getInvoiceFiles(invoiceId)

    }
     **/
}