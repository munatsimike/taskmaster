package com.example.taskmaster.data.repository


import com.example.taskmaster.data.local.LocalDataSource
import com.example.taskmaster.data.mapper.APIResponseMapper.toApiResponseMessage
import com.example.taskmaster.data.mapper.ProjectMapper.toListOfDomainProject
import com.example.taskmaster.data.remote.RemoteDataSource
import com.example.taskmaster.data.remote.api.NetworkResponse
import com.example.taskmaster.ui.model.APIResponseMessage
import com.example.taskmaster.domain.model.project.Project
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * ProjectsRepo is responsible for managing data operations by communicating
 * with various data sources, such as remote and local sources, to fetch, process,
 * and convert data into domain models suitable for use within the app.
 */
class ProjectsRepo @Inject constructor(
    override val remoteDataSource: RemoteDataSource,
    val localDataSource: LocalDataSource,
) : BaseRepository(remoteDataSource) {

    fun addOrEditNewProject(addEditProject: Project): Flow<NetworkResponse<APIResponseMessage>> =
        processApiResponse(
            call = { remoteDataSource.addOrEditNewProject(addEditProject) },
            onSuccess = { response ->
                response.toApiResponseMessage()
            }
        )

    fun deleteProject(projectId: String): Flow<NetworkResponse<APIResponseMessage>> =
        processApiResponse(
            call = { remoteDataSource.deleteProject(projectId) },
            onSuccess = { response ->
                response
            }
        )

    fun getProjects(): Flow<NetworkResponse<List<Project>>> =
        processApiResponse(call = { remoteDataSource.getProjects() } // Wrap the call in a suspending lambda
        ) { response ->
            response.toListOfDomainProject() // Transform the response body to a list of domain models
        }
}