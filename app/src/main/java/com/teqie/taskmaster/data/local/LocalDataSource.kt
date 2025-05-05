package com.teqie.taskmaster.data.local

import com.teqie.taskmaster.data.local.db.enties.BudgetPhaseEntity
import com.teqie.taskmaster.data.local.db.enties.DashboardEntity
import com.teqie.taskmaster.data.local.db.enties.InvoiceEntity
import com.teqie.taskmaster.data.local.db.enties.LoggedInUserEntity
import com.teqie.taskmaster.data.local.db.enties.ProjectEntity
import com.teqie.taskmaster.data.local.db.enties.TeamMemberEntity
import com.teqie.taskmaster.data.local.preferences.AccessToken
import com.teqie.taskmaster.domain.util.FileExtension
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody

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

    suspend fun saveBudgetPhase(budgetPhases: List<BudgetPhaseEntity>)

    fun getBudgetPhases(projectId: String): Flow<List<BudgetPhaseEntity>>

    suspend fun saveInvoices(invoices: List<InvoiceEntity>)

    fun getInvoices(budgetId: String): Flow<List<InvoiceEntity>>

    suspend fun saveFileToStorage(
        responseBody: ResponseBody,
        fileName: String,
        fileType: FileExtension,
        progress: (Int) -> Unit
    )

    fun fetchProjectTeamMembers(projectId: String): Flow<List<TeamMemberEntity>>

}