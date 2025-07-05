package com.teqie.taskmaster.data.local


import com.teqie.taskmaster.data.local.db.dao.BudgetPhaseDao
import com.teqie.taskmaster.data.local.db.dao.DashboardDao
import com.teqie.taskmaster.data.local.db.dao.GalleryImageDao
import com.teqie.taskmaster.data.local.db.dao.InvoiceDao
import com.teqie.taskmaster.data.local.db.dao.LoggedInUserDao
import com.teqie.taskmaster.data.local.db.dao.OrfiDao
import com.teqie.taskmaster.data.local.db.dao.ProjectDao
import com.teqie.taskmaster.data.local.db.dao.ScheduleDao
import com.teqie.taskmaster.data.local.db.dao.TeamMemberDao
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
import com.teqie.taskmaster.data.local.preferences.EncryptedPreferenceManager
import com.teqie.taskmaster.domain.util.FileExtension
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val encryptedPreferenceManager: EncryptedPreferenceManager,
    private val loggedInUserDao: LoggedInUserDao,
    private val projectDao: ProjectDao,
    private val dashboardDao: DashboardDao,
    private val budgetPhaseDao: BudgetPhaseDao,
    private val invoiceDao: InvoiceDao,
    private val internalStorage: FileStorageManager,
    private val teamMemberDao: TeamMemberDao,
    private val scheduleDao: ScheduleDao,
    private val galleryImageDao: GalleryImageDao,
    private val orfiDao: OrfiDao
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

    override suspend fun deleteProjects() {
        projectDao.deleteProjects()
    }

    override suspend fun deleteBudgetPhases() {
        budgetPhaseDao.deleteBudgetPhases()
    }

    override suspend fun deleteFolders() {
        galleryImageDao.deleteFolders()
    }

    override suspend fun deleteImages() {
        galleryImageDao.deleteImages()
    }

    override suspend fun deleteOrfis() {
        orfiDao.deleteOrfis()
    }

    override suspend fun deleteInvoices() {
        invoiceDao.deleteInvoices()
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
        return projectDao.getAllProjects()
    }

    override suspend fun saveDashboard(dashboard: DashboardEntity) {
        dashboardDao.saveDashboard(dashboard)
    }

    override fun getDashBoard(projectId: String): Flow<DashboardEntity?> {
        return dashboardDao.fetchDashboard(projectId)
    }

    override suspend fun saveBudgetPhase(budgetPhases: List<BudgetPhaseEntity>) {
        budgetPhaseDao.saveBudgetPhase(budgetPhases)
    }

    override fun getBudgetPhases(projectId: String): Flow<List<BudgetPhaseEntity>> {
        return budgetPhaseDao.fetchBudgetPhase(projectId)
    }

    override suspend fun saveInvoices(invoices: List<InvoiceEntity>) {
        invoiceDao.saveInvoices(invoices)
    }

    override fun getInvoices(budgetId: String): Flow<List<InvoiceEntity>> =
        invoiceDao.fetchInvoices(budgetId)

    override suspend fun saveFileToStorage(
        responseBody: ResponseBody,
        fileName: String,
        fileType: FileExtension,
        progress: (Int) -> Unit
    ) {
        internalStorage.saveFileToStorage(responseBody, fileName, fileType, progress)
    }

    override fun fetchProjectTeamMembers(projectId: String): Flow<List<TeamMemberEntity>> {
        return teamMemberDao.getProjectTeamMembers(projectId)
    }

    override suspend fun saveTeamMembers(members: List<TeamMemberEntity>) {
        teamMemberDao.saveTeamMembers(members)
    }

    override fun fetchProjectSchedule(projectId: String): Flow<List<ScheduleEntity>> {
        return scheduleDao.fetchSchedules(projectId)
    }

    override suspend fun saveProjectSchedule(schedules: List<ScheduleEntity>) {
        scheduleDao.saveSchedules(schedules)
    }

    override suspend fun saveImages(images: List<GalleryImageEntity>) {
        galleryImageDao.saveImages(images)
    }

    override fun fetchImages(folderId: String): Flow<List<GalleryImageEntity>> =
        galleryImageDao.fetchImages(folderId)

    override suspend fun saveFolders(folders: List<FolderEntity>) {
        galleryImageDao.saveFolders(folders)
    }

    override fun fetchFolders(projectId: String): Flow<List<FolderEntity>> =
        galleryImageDao.fetchFolders(projectId)

    override suspend fun saveOrfi(orfis: List<OrfiEntity>) {
        orfiDao.saveOrfi(orfis)
    }

    override fun fetchOrfis(projectId: String): Flow<List<OrfiEntity>> {
        return orfiDao.fetchOrfi(projectId)
    }

    override fun fetchOrfiFiles(projectId: String): Flow<List<OrfiFileEntity>> {
       return orfiDao.fetchOrfiFile(projectId)
    }

    override suspend fun saveOrfiFile(files: List<OrfiFileEntity>) {
        orfiDao.saveOrfiFile(files)
    }
}