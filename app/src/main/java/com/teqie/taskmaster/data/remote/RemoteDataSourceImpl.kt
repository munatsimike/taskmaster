package com.teqie.taskmaster.data.remote

import com.teqie.taskmaster.data.mapper.project.ProjectDomainToDtoMapper.toCreateNewProjectRequest
import com.teqie.taskmaster.data.mapper.project.ProjectDomainToDtoMapper.toUpdateProjectRequestDto
import com.teqie.taskmaster.data.remote.api.service.AuthService
import com.teqie.taskmaster.data.remote.api.service.DashboardService
import com.teqie.taskmaster.data.remote.api.service.ProjectService
import com.teqie.taskmaster.data.remote.dto.ProjectResponseDto
import com.teqie.taskmaster.data.remote.dto.auth.LoginRequestDto
import com.teqie.taskmaster.data.remote.dto.dashboard.DashboardAPiResponseDto
import com.teqie.taskmaster.data.remote.dto.user.UserApiResponseDto
import com.teqie.taskmaster.domain.model.RemoteResponse
import com.teqie.taskmaster.domain.model.project.Project
import com.teqie.taskmaster.ui.model.ResponseMessage
import retrofit2.Response
import javax.inject.Inject

/**
 *  * RemoteDataSourceImpl is responsible for interacting with the remote server to access and manage data.
 *  * It acts as a bridge between the data service (Retrofit interface) and the repository layer,
 */
class RemoteDataSourceImpl @Inject constructor(
    private val authService: AuthService,
    private val projectService: ProjectService,
    private val dashboardService: DashboardService
): RemoteDataSource {
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

    override suspend fun addOrEditNewProject(projectRequest: Project, isEditing: Boolean): Response<out RemoteResponse> {
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
}