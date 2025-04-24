package com.example.taskmaster.data.repository

import com.example.taskmaster.data.local.LocalDataSource
import com.example.taskmaster.data.local.db.enties.ProjectEntity
import com.example.taskmaster.data.mapper.APIResponseMapper.toApiResponseMessage
import com.example.taskmaster.data.mapper.project.ProjectDtoToEntityMapper.toEntityList
import com.example.taskmaster.data.mapper.project.ProjectEntityToDomainMapper.toDomainModel
import com.example.taskmaster.data.remote.RemoteDataSource
import com.example.taskmaster.data.remote.api.Resource
import com.example.taskmaster.domain.ProjectRepository
import com.example.taskmaster.domain.model.project.Project
import com.example.taskmaster.ui.model.ResponseMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * ProjectsRepoImpl is responsible for managing data operations by communicating
 * with various data sources, such as remote and local sources, to fetch, process,
 * and convert data into domain models suitable for use within the app.
 */
class ProjectsRepoImpl @Inject constructor(
    private val remoteDataSourceImpl: RemoteDataSource,
    private val localDataSourceImpl: LocalDataSource
) : ProjectRepository, BaseRepository() {

    override fun addOrEditNewProject(
        addEditProject: Project,
        isEditing: Boolean
    ): Flow<Resource<ResponseMessage>> =
        processApiResponse(
            call = { remoteDataSourceImpl.addOrEditNewProject(addEditProject, isEditing) },
            onSuccess = { response ->
                response.toApiResponseMessage()
            }
        )

    override fun deleteProject(projectId: String): Flow<Resource<ResponseMessage>> =
        processApiResponse(
            call = { remoteDataSourceImpl.deleteProject(projectId) },
            onSuccess = { response ->
                response
            }
        )

    override fun getProjects(): Flow<Resource<List<Project>>> = flow {
        emitAll(fetchFromLocalDb(
            fetchEntities = { localDataSourceImpl.getAllProjects() },
            fromEntityMapper = { it.toDomainModel() }
        ))
    }

    // 2. Update local database from remote
    override fun updateProjects(): Flow<Resource<Unit>> = flow {
        emitAll(processAndCacheApiResponse(
            call = { remoteDataSourceImpl.getProjects() },
            toEntityMapper = { it.toEntityList() },
            saveEntities = { localDataSourceImpl.saveProjects(it) }
        ))
    }

    override suspend fun saveProjectsToDb(projects: List<ProjectEntity>) {
        localDataSourceImpl.saveProjects(projects)
    }

    override fun getAllProjects(): Flow<List<ProjectEntity>> = localDataSourceImpl.getAllProjects()
}