package com.teqie.taskmaster.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.teqie.taskmaster.data.local.db.enties.DashboardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DashboardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDashboard(dashboard: DashboardEntity)

    @Query("SELECT * FROM dashboard WHERE projectId=:projectId")
    fun fetchDashboard(projectId: String): Flow<DashboardEntity?>
}