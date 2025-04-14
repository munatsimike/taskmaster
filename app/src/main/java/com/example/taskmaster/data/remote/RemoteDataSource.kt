package com.example.taskmaster.data.remote

import com.example.taskmaster.data.mapper.ProjectMapper.toCreateNewProjectRequest
import com.example.taskmaster.data.mapper.ProjectMapper.toUpdateProjectRequestDto
import com.example.taskmaster.data.remote.api.service.AuthService
import com.example.taskmaster.data.remote.api.service.ProjectService
import com.example.taskmaster.data.remote.dto.project.ProjectDto
import com.example.taskmaster.data.remote.dto.user.UserApiResponseDto
import com.example.taskmaster.domain.LoginRequest
import com.example.taskmaster.domain.model.APIResponse
import com.example.taskmaster.ui.model.APIResponseMessage
import com.example.taskmaster.domain.model.project.Project
import retrofit2.Response
import javax.inject.Inject

/**
 *  * RemoteDataSource is responsible for interacting with the remote server to access and manage data.
 *  * It acts as a bridge between the data service (Retrofit interface) and the repository layer,
 */
class RemoteDataSource @Inject constructor(
    private val authService: AuthService,
    private val projectService: ProjectService,
) {
    /**dataService
     * attempts to login through the data service and returns server response wrapped in a response object
     * @param loginRequest contains the username and password
     */
    suspend fun login(loginRequest: LoginRequest): Response<UserApiResponseDto> {
        // make api request using retrofit service and returns the server response
        return authService.login(loginRequest)
    }

    // The following functions implement Create, Read, Update, and Delete (CRUD) operations for Project entities in the remote data source.
    suspend fun getProjects(): Response<List<ProjectDto>> {
        return projectService.getProjects()
    }

    suspend fun addOrEditNewProject(projectRequest: Project): Response<out APIResponse> {
        if (projectRequest.isEditing) {
            val updateRequest = projectRequest.toUpdateProjectRequestDto()
            return projectService.updateProject(updateRequest.id, updateRequest)

        }
        return projectService.createNewProject(projectRequest.toCreateNewProjectRequest())
    }

    suspend fun deleteProject(projectId: String): Response<APIResponseMessage> {
        return projectService.deleteProject(projectId)
    }
}