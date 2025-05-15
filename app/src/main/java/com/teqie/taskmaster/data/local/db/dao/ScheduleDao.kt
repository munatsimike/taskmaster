package com.teqie.taskmaster.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.teqie.taskmaster.data.local.db.enties.ScheduleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSchedules(schedule: List<ScheduleEntity>)

    @Query("SELECT * FROM schedule WHERE projectId=:projectId")
    fun fetchSchedules(projectId: String): Flow<List<ScheduleEntity>>
}