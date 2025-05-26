package com.teqie.taskmaster.data.repository

import com.teqie.taskmaster.data.local.LocalDataSource
import com.teqie.taskmaster.data.local.db.enties.ProjectEntity
import com.teqie.taskmaster.data.mapper.APIResponseMapper.toApiResponseMessage
import com.teqie.taskmaster.data.mapper.project.ProjectDtoToEntityMapper.toEntityList
import com.teqie.taskmaster.data.mapper.project.ProjectEntityToDomainMapper.toDomainModel
import com.teqie.taskmaster.data.remote.RemoteDataSource
import com.teqie.taskmaster.domain.ProjectRepository
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.project.Project
import com.teqie.taskmaster.ui.model.ResponseMessage
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
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : ProjectRepository, BaseRepository() {

    override fun addOrEditNewProject(
        addEditProject: Project,
        isEditing: Boolean
    ): Flow<Resource<ResponseMessage>> =
        processApiResponse(
            call = { remoteDataSource.addOrEditNewProject(addEditProject, isEditing) },
            onSuccess = { response ->
                response.toApiResponseMessage()
            }
        )

    override fun deleteProject(projectId: String): Flow<Resource<ResponseMessage>> =
        processApiResponse(
            call = { remoteDataSource.deleteProject(projectId) },
            onSuccess = { response ->
                response
            }
        )

    override fun getProjects(): Flow<Resource<List<Project>>> = flow {
        emitAll(fetchFromLocalDb(
            fetchEntities = { localDataSource.getAllProjects() },
            fromEntityMapper = { it.toDomainModel() }
        ))
    }

    // 2. Update local database from remote
    override fun syncProjectsToLocalDb(): Flow<Resource<Unit>> = flow {
        emitAll(processAndCacheApiResponse(
            call = { remoteDataSource.getProjects() },
            toEntityMapper = { it.toEntityList() },
            clearTable = {localDataSource.deleteProjects()},
            saveEntities = { localDataSource.saveProjects(it) }
        ))
    }

    override fun getAllProjects(): Flow<List<ProjectEntity>> = localDataSource.getAllProjects()
}