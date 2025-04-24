package com.example.taskmaster.data.local


import com.example.taskmaster.data.local.db.dao.DashboardDao
import com.example.taskmaster.data.local.db.dao.LoggedInUserDao
import com.example.taskmaster.data.local.db.dao.ProjectDao
import com.example.taskmaster.data.local.db.enties.DashboardEntity
import com.example.taskmaster.data.local.db.enties.LoggedInUserEntity
import com.example.taskmaster.data.local.db.enties.ProjectEntity
import com.example.taskmaster.data.local.preferences.AccessToken
import com.example.taskmaster.data.local.preferences.EncryptedPreferenceManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val encryptedPreferenceManager: EncryptedPreferenceManager,
    private val loggedInUserDao: LoggedInUserDao,
    private val projectDao: ProjectDao,
    private val dashboardDao: DashboardDao
) : LocalDataSource {

    // Functions to save and retrieve access tokens (SharedPreferences)
    override fun getAccessToken(key: String): Flow<AccessToken> {
        return encryptedPreferenceManager.getData(key)
    }

    override suspend fun saveAccessToken(accessToken: AccessToken) {
        encryptedPreferenceManager.saveUpdate(accessToken)
    }

    override suspend fun deleteAccessToken(accessToken: AccessToken) {
        encryptedPreferenceManager.saveUpdate(accessToken) // Clear the token
    }

    // Functions to save and retrieve logged-in user (Room Database)
    override suspend fun saveLoggedInUser(userEntity: LoggedInUserEntity) {
        loggedInUserDao.saveUser(userEntity)
    }

    override suspend fun deleteLoggedInUser() {
        loggedInUserDao.deleteLoggedInUser()
    }

    override fun getLoggedInUser(): Flow<LoggedInUserEntity?> {
        return loggedInUserDao.getLoggedInUser()
    }

    // Functions to save and retrieve projects (Room Database)
    override suspend fun saveProjects(projects: List<ProjectEntity>) {
       projectDao.saveProjects(projects)
    }

    override fun getAllProjects(): Flow<List<ProjectEntity>> {
     return  projectDao.getAllProjects()
    }

    override suspend fun saveDashboard(dashboard: DashboardEntity) {
        dashboardDao.saveDashboard(dashboard)
    }

    override fun getDashBoard(): Flow<DashboardEntity?> {
       return dashboardDao.fetchDashboard()
    }
}