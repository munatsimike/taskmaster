package com.example.taskmaster.data.local

import com.example.taskmaster.data.local.db.enties.DashboardEntity
import com.example.taskmaster.data.local.db.enties.LoggedInUserEntity
import com.example.taskmaster.data.local.db.enties.ProjectEntity
import com.example.taskmaster.data.local.preferences.AccessToken
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getAccessToken(key: String): Flow<AccessToken>

    suspend fun saveAccessToken(accessToken: AccessToken)

    suspend fun deleteAccessToken(accessToken: AccessToken)

    suspend fun saveLoggedInUser(userEntity: LoggedInUserEntity)

    suspend fun deleteLoggedInUser()

    fun getLoggedInUser(): Flow<LoggedInUserEntity?>

    suspend fun saveProjects(projects: List<ProjectEntity>)

    fun getAllProjects(): Flow<List<ProjectEntity>>

    suspend fun saveDashboard(dashboard: DashboardEntity)
    
    fun getDashBoard(projectId: String): Flow<DashboardEntity?>
}