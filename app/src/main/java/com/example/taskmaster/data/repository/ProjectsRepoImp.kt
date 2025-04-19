package com.example.taskmaster.data.repository

import com.example.taskmaster.data.local.LocalDataSource
import com.example.taskmaster.data.local.db.enties.ProjectEntity
import com.example.taskmaster.data.mapper.APIResponseMapper.toApiResponseMessage
import com.example.taskmaster.data.mapper.BudgetPhaseMapper.toLstOfDashBoardBudgetPhaseModel
import com.example.taskmaster.data.mapper.OrfiMapper.toLstOfOrfiModel
import com.example.taskmaster.data.mapper.ScheduleMapper.toListOfScheduleModel
import com.example.taskmaster.data.mapper.project.ProjectDomainToDtoMapper.toTotalsModel
import com.example.taskmaster.data.mapper.project.ProjectDtoToEntityMapper.toEntityList
import com.example.taskmaster.data.mapper.project.ProjectEntityToDomainMapper.toDomainModel
import com.example.taskmaster.data.remote.RemoteDataSource
import com.example.taskmaster.data.remote.api.Resource
import com.example.taskmaster.domain.ProjectRepository
import com.example.taskmaster.domain.model.project.Project
import com.example.taskmaster.ui.model.APIResponseMessage
import com.example.taskmaster.ui.model.DashboardData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * ProjectsRepoImp is responsible for managing data operations by communicating
 * with various data sources, such as remote and local sources, to fetch, process,
 * and convert data into domain models suitable for use within the app.
 */
class ProjectsRepoImp @Inject constructor(
    override val remoteDataSource: RemoteDataSource,
    val localDataSourceImp: LocalDataSource
) : ProjectRepository, BaseRepository(remoteDataSource) {

    override fun addOrEditNewProject(
        addEditProject: Project,
        isEditing: Boolean
    ): Flow<Resource<APIResponseMessage>> =
        processApiResponse(
            call = { remoteDataSource.addOrEditNewProject(addEditProject, isEditing) },
            onSuccess = { response ->
                response.toApiResponseMessage()
            }
        )

    override fun deleteProject(projectId: String): Flow<Resource<APIResponseMessage>> =
        processApiResponse(
            call = { remoteDataSource.deleteProject(projectId) },
            onSuccess = { response ->
                response
            }
        )

    override fun getProjects(): Flow<Resource<List<Project>>> =
        processAndCacheApiResponse(
            call = { remoteDataSource.getProjects() },
            toEntityMapper = { it.toEntityList() },
            saveEntities = { localDataSourceImp.saveProjects(it) },
            fromEntityMapper = { it.toDomainModel() },
            fetchEntities = { localDataSourceImp.getAllProjects() }
        )

    override fun getProjectDashboard(projectId: String): Flow<Resource<DashboardData>> =
        processApiResponse(
            call = { remoteDataSource.getProjectDashboard(projectId) }
        ) { response ->
            val totalsModel = response.totals.toTotalsModel()
            DashboardData(
                budgetPhases = response.budgetPhases.toLstOfDashBoardBudgetPhaseModel(),
                orfis = response.orfis.toLstOfOrfiModel(),
                schedules = response.schedules.toListOfScheduleModel(),
                totals = totalsModel
            )
        }

    override suspend fun saveProjectsToDb(projects: List<ProjectEntity>) {
        localDataSourceImp.saveProjects(projects)
    }

    override fun getAllProjects(): Flow<List<ProjectEntity>> = localDataSourceImp.getAllProjects()

}