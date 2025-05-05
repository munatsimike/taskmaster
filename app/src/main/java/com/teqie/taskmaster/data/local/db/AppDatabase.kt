package com.teqie.taskmaster.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.teqie.taskmaster.data.local.db.converters.DashboardConverters
import com.teqie.taskmaster.data.local.db.dao.BudgetPhaseDao
import com.teqie.taskmaster.data.local.db.dao.DashboardDao
import com.teqie.taskmaster.data.local.db.dao.InvoiceDao
import com.teqie.taskmaster.data.local.db.dao.LoggedInUserDao
import com.teqie.taskmaster.data.local.db.dao.ProjectDao
import com.teqie.taskmaster.data.local.db.enties.BudgetPhaseEntity
import com.teqie.taskmaster.data.local.db.enties.DashboardEntity
import com.teqie.taskmaster.data.local.db.enties.InvoiceEntity
import com.teqie.taskmaster.data.local.db.enties.LoggedInUserEntity
import com.teqie.taskmaster.data.local.db.enties.ProjectEntity

@Database(
    entities = [LoggedInUserEntity::class, ProjectEntity::class, DashboardEntity::class, BudgetPhaseEntity::class, InvoiceEntity::class],
    version = 1
)
@TypeConverters(DashboardConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val loggedInDao: LoggedInUserDao
    abstract val projectDao: ProjectDao
    abstract val dashboardDao: DashboardDao
    abstract val budgetPhaseDao: BudgetPhaseDao
    abstract val invoiceDao: InvoiceDao
}