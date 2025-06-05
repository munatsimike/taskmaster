package com.teqie.taskmaster.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.teqie.taskmaster.data.local.db.converters.DashboardConverters
import com.teqie.taskmaster.data.local.db.converters.ImageConverters
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

@Database(
    entities = [LoggedInUserEntity::class, ProjectEntity::class, DashboardEntity::class,
        BudgetPhaseEntity::class, InvoiceEntity::class, TeamMemberEntity::class, ScheduleEntity::class,
        GalleryImageEntity::class, FolderEntity::class, OrfiEntity::class,OrfiFileEntity::class],
    version = 1
)
@TypeConverters(DashboardConverters::class, ImageConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val loggedInDao: LoggedInUserDao
    abstract val projectDao: ProjectDao
    abstract val dashboardDao: DashboardDao
    abstract val budgetPhaseDao: BudgetPhaseDao
    abstract val invoiceDao: InvoiceDao
    abstract val teamMemberDao:  TeamMemberDao
    abstract val scheduleDao: ScheduleDao
    abstract val galleryDao: GalleryImageDao
    abstract val orfiDao: OrfiDao
}