package com.teqie.taskmaster.data.local

import com.teqie.taskmaster.data.local.db.enties.BudgetPhaseEntity
import com.teqie.taskmaster.data.local.db.enties.DashboardEntity
import com.teqie.taskmaster.data.local.db.enties.FolderEntity
import com.teqie.taskmaster.data.local.db.enties.GalleryImageEntity
import com.teqie.taskmaster.data.local.db.enties.InvoiceEntity
import com.teqie.taskmaster.data.local.db.enties.LoggedInUserEntity
import com.teqie.taskmaster.data.local.db.enties.OrfiEntity
import com.teqie.taskmaster.data.local.db.enties.OrfiFileEntity
import com.teqie.taskmaster.data.local.db.enties.ProjectEntity
import com.teqie.taskmaster.data.local.db.enties.ScheduleEntity
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

    suspend fun saveTeamMembers(members: List<TeamMemberEntity>)

    fun fetchProjectSchedule(projectId: String): Flow<List<ScheduleEntity>>

    suspend fun saveProjectSchedule(schedules: List<ScheduleEntity>)

    suspend fun saveImages(images: List<GalleryImageEntity>)

    fun fetchImages(folderId: String): Flow<List<GalleryImageEntity>>

    suspend fun saveFolders(folders: List<FolderEntity>)

    fun fetchFolders(projectId: String): Flow<List<FolderEntity>>

    suspend fun saveOrfi(orfis: List<OrfiEntity>)

    fun fetchOrfis(projectId: String): Flow<List<OrfiEntity>>

    fun fetchOrfiFiles(projectId: String): Flow<List<OrfiFileEntity>>

    suspend fun saveOrfiFile(files: List<OrfiFileEntity>)

    suspend fun deleteProjects()
    suspend fun deleteBudgetPhases()

    suspend fun deleteFolders()
    suspend fun deleteImages()
}
